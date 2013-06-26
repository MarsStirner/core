#!/usr/bin/env python

from __future__ import unicode_literals

from xml.etree.ElementTree import parse as parse
from xml.etree.ElementTree import XMLParser

from ConfigParser import ConfigParser

from codecs import open
from collections import namedtuple
from collections import defaultdict
from contextlib import closing, contextmanager
from re import search
from sys import argv as args

import MySQLdb as db
import os
import string
import sys

###############################################################################
# Classes
###############################################################################
class CannotProcessRlsError(Exception):
	msg = "NONE"
	props = {}

	def __init__(self, msg, props):
		self.msg = msg
		self.props = props

DatabaseEntry = namedtuple('DatabaseEntry', ['id', 'tablename', 'code', 'fields', 'do_insert'])

###############################################################################
# Utils
###############################################################################
def to_lower_case(name):
	return name[:1].lower() + name[1:]

def row_to_dict(desc, row):
	result = {}
	for (name, value) in zip(desc, row) :
		result[name[0]] = value
	return result

def row_to_de(tablename, desc, row):
	field_names = tables[tablename]
	row_dict = row_to_dict(desc, row)
	fields = dict([(fn, row_dict[fn]) for fn in field_names])
	return DatabaseEntry(
		row_dict['id'],
		tablename,
		None,
		fields,
		False)

def to_de(code, props):
	def function_to_tablename():
		func_name = sys._getframe(2).f_code.co_name.replace("get_", "")
		tablename = string.capwords(func_name, "_").replace("_", "")
		return to_lower_case(tablename)
	non_empty_props = filter(lambda (k, v): v != "", props.items())
	if len(non_empty_props) == 0:
		return None
	else:
		return DatabaseEntry(
			None,
			function_to_tablename(),
			code,
			dict(non_empty_props),
			False)

def _(props, *keys):
	return " ".join([props[k] for k in keys if k in props])

def freeze(dict):
	return frozenset(dict.items())

###############################################################################
# Data getters
###############################################################################
def get_code(props):
	try:
		return search(r".*/(.*).html", props["URL"]).group(1).strip();
	except Exception:
		raise CannotProcessRlsError("Cannot parse RLS entry code", props)

def get_rls_nomen(code, props):
	return to_de(code, {
		"code": int(code)
	})

def get_rls_trade_name(code, props):
	return to_de(code, {
		"name": _(props, "TRADENAME")
	})

def get_rls_inp_name(code, props):
	name = None
	latName = None
	
	if "NDV" in props:
		s = search(r"(.*)\s*\((.*)\)", props["NDV"])
		if s is not None:
			name = s.group(1)
			latName = s.group(2)

	if "TRADENAME" in props and name is None:
		name = _(props, "TRADENAME")
	if "LATNAME" in props and latName is None:
		latName = _(props, "LATNAME")

	if name is not None and latName is not None:
		name = name.strip()
		latName = latName.strip()
	elif name is not None:
		name = name.strip()
		latName = ""
	else:
		raise CannotProcessRlsError("Cannot parse RLS entry INP name", props)			

	return to_de(code, {
		"name": name,
		"latName": latName
	})

def get_rls_dosage(code, props):
	result = None
	if "DFMASS" in props:
		result = _(props, "DFMASS", "DFMASS_SHORTNAME")
	elif "DFCONC" in props:
		result = _(props, "DFCONC", "DFCONC_SHORTNAME")
	elif "DFACT" in props:
		result = _(props, "DFACT", "DFACT_SHORTNAME")
	elif "DFSIZE" in props:
		result = _(props, "DFSIZE", "DFSIZE_SHORTNAME")
	elif "DFCHAR_SHORTNAME" in props:
		result = _(props, "DFCHAR_SHORTNAME")
	elif "DRUGDOSE" in props:
		result = _(props, "DRUGDOSE")
	else:
		# raise CannotProcessRlsError("Cannot parse RLS entry dosage", props)
		result = ""
	return to_de(code, {
		"name": result
	})

def get_rls_form(code, props):
	return to_de(code, {
		"name": _(props, "DRUGFORM_SHORTNAME")
	})

def get_rls_filling(code, props):
	return to_de(code, {
		"name": _(props,
			"PPACK_SHORTNAME",
			"PPACKMASS",
			"PPACKMASS_SHORTNAME",
			"PPACKVOLUME",
			"PPACKVOLUME_SHORTNAME",
			"DRUGSINPPACK",
			"DRUGSET_SHORTNAME")
	})

def get_rls_packing(code, props):
	return to_de(code, {
		"name": _(props, "UPACK_SHORTNAME", "PPACKINUPACK")
	})

###############################################################################
# SQL generators
###############################################################################
def get_next_id(de):
        if ids[de.tablename] is None: nid = 1
	else: nid = ids[de.tablename] + 1
	ids[de.tablename] = nid
	return nid

def insert_into_refs(de):
	nid = None
	do_insert = False
	if freeze(de.fields) in refs[de.tablename]:
		nid = refs[de.tablename][freeze(de.fields)]
	elif de.id is not None:
		nid = de.id
		refs[de.tablename][freeze(de.fields)] = nid
	else:
		nid = get_next_id(de)
		refs[de.tablename][freeze(de.fields)] = nid
		do_insert = True
	return de._replace(id = nid, do_insert = do_insert)

def sql_insert_ref_from_de(de, unused):
	tablename = de.tablename

	def _(x): return "`{}`".format(x)
	columns = ", ".join([_(k) for k in de.fields.keys()])
	columns = "`id`, " + columns

	def _(x): return '"{}"'.format(x)
	values = ", ".join([_(v) for v in de.fields.values()])
	values = str(de.id) + ", " + values

	return "INSERT INTO `{tablename}` ({columns}) VALUES ({values});".format(
		tablename=tablename, columns=columns, values=values
	)

def sql_insert_master_from_de(de, entries):
	tablename = de.tablename

	def _(x): return "`{}`".format(x)
	columns = ", ".join([_(k) for k in de.fields.keys()]) + ", " + \
		", ".join([_(c) for (tn, c) in ref_columns.items() if tn in entries])
	columns = "`id`, " + columns

	def _(x): return '{}'.format(x)
	values = ", ".join([_(v) for v in de.fields.values()]) + ", " + \
		", ".join([_(str(entries[tn].id)) for tn in ref_columns.keys() if tn in entries])
	values = str(de.id) + ", " + values

	return "INSERT INTO `{tablename}` ({columns}) VALUES ({values});".format(
		tablename=tablename, columns=columns, values=values
	)

###############################################################################
# Internal data
###############################################################################
getters = [
	(get_rls_trade_name, sql_insert_ref_from_de),
	(get_rls_inp_name, sql_insert_ref_from_de),
	(get_rls_dosage, sql_insert_ref_from_de),
	(get_rls_form, sql_insert_ref_from_de),
	(get_rls_filling, sql_insert_ref_from_de),
	(get_rls_packing, sql_insert_ref_from_de),
	(get_rls_nomen, sql_insert_master_from_de)
]

# tablename -> (values -> id)
refs = defaultdict(dict)
# tablename -> id
ids = defaultdict(int)

sql_inserts = defaultdict(dict)

ref_columns = {
	"rlsTradeName": "tradeName_id",
	"rlsInpName": "INPName_id",
	"rlsDosage": "dosage_id",
	"rlsForm": "form_id",
	"rlsFilling": "filling_id",
	"rlsPacking": "packing_id"
}

tables = {
	"rlsTradeName": ["name"],
	"rlsInpName":   ["name", "latName"],
	"rlsDosage":    ["name"],
	"rlsForm":      ["name"],
	"rlsFilling":   ["name"],
	"rlsPacking":   ["name"],
	"rlsNomen":     ["code"]
}

tables_in_order = [
	"rlsTradeName",
	"rlsInpName",
	"rlsDosage",
	"rlsForm",
	"rlsFilling",
	"rlsPacking",
	"rlsNomen"
]

CONFIG_FILENAME = os.path.join(os.path.dirname(__file__), 'rls-sync.conf')

###############################################################################
# Database sync
###############################################################################
@contextmanager
def open_db_connection():
	params = get_config()
	with closing(db.connect(host=params['host'],
							user=params['username'],
							passwd=params['password'],
							db=params['dbname'],
							charset='utf8',
							use_unicode=True)) as c:
		c.autocommit(False)
		yield c

def get_config():
	p = ConfigParser()
	p.read([CONFIG_FILENAME])
	return {
		'host': p.get(b'database', b'host'),
		'username': p.get(b'database', b'username'),
		'password': p.get(b'database', b'password'),
		'dbname': p.get(b'database', b'dbname'),
	}

def init_data():
	with open_db_connection() as conn:
		c = conn.cursor()
		for table in tables.keys():
			sql = "SELECT MAX(id) FROM {tablename}".format(tablename=table)
			c.execute(sql)
			ids[table] = c.fetchone()[0]

			sql = "SELECT * from {tablename}".format(tablename=table)
			c.execute(sql)
			desc = c.description
			for row in c:
				de = row_to_de(table, desc, row)
				de = insert_into_refs(de)

###############################################################################
# Main
###############################################################################
def process(code, props):
	entries = {}
	for (getter, generator) in getters:
		de = getter(code, props)
		if de is not None:
			de = insert_into_refs(de)
			entries[de.tablename] = de
			if de.do_insert:
				sql = generator(de, entries)
				sql_inserts[de.tablename][de.id] = sql

def generate_sql():
	result = []
	for table in tables_in_order:
		result.extend(sql_inserts[table].values())
	return result

def write_result(header, sqls):
	f = open("rls-sync.update.sql", "w+", encoding="utf-8")
	f.write(header + "\n")
	for sql in sqls:
		f.write(sql + "\n")
	f.close()

def parse_xml(file_name):
	return parse(file_name, XMLParser(encoding="utf-8"))

def aggregate(xml):
	result = {}
	for e in xml.findall("P"):
		result[e.get("name")] = e.text.strip()
	return result

if __name__ == "__main__":
	init_data()

	for rls_file in args:
		try:
			if rls_file.endswith("rls-sync.py"):
				continue
			xml = parse_xml(rls_file)
			props = aggregate(xml)
			code = get_code(props)
			process(code, props)
		except CannotProcessRlsError as e:
			print e.msg + " -> " + rls_file
			print e.props.keys()
			continue

	header = "USE `" + get_config()['dbname'] + "`;"
	sqls = generate_sql()

	write_result(header, sqls)
