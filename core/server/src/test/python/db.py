import MySQLdb as db

from contextlib import closing, contextmanager
from ConfigParser import ConfigParser

CONFIG_FILENAME = 'test.ini'
HOST_NAME = ''

def initialize():
    p = ConfigParser()
    p.read([CONFIG_FILENAME])
    HOST_NAME = p.get('db', 'hostname')

@contextmanager
def connect(host=HOST_NAME, username="root", password="root", dbname="s11r64"):
    with closing(db.connect(host=host,
                            user=username,
                            passwd=password,
                            db=dbname,
                            charset='utf8',
                            use_unicode=True)) as c:
        yield c

def query(conn, sql):
    c = conn.cursor()
    c.execute(sql)
    rows = c.fetchall()
    return rows

def execute(conn, sql):
    c = conn.cursor()
    c.execute(sql)

def insert(conn, sql):
    c = conn.cursor()
    c.execute(sql)
    sqlLastInsertedId = "SELECT LAST_INSERT_ID()"
    c.execute(sqlLastInsertedId)
    result = c.fetchone()
    return result[0]

def anyId(conn, tableName):
    sql = 'select id from {tableName} limit 1'.format(tableName=tableName)
    rows = query(conn, sql)
    if len(rows) > 0:
	return rows[0][0]
    else:
	raise Exception('No rows found in table {tableName}'.format(tableName=tableName))

def delete(conn, tableName, id):
    sql = 'delete from {tableName} where id = {id}'.format(tableName=tableName, id=id)
    execute(conn, sql)

valueTables = [
		'ActionProperty_Action',
		'ActionProperty_CureEpicrisis',
		'ActionProperty_Date',
		'ActionProperty_DiagnosticEpicrisis',
		'ActionProperty_Double',
		'ActionProperty_HospitalBed',
		'ActionProperty_Image',
		'ActionProperty_ImageMap',
		'ActionProperty_Integer',
		'ActionProperty_Job_Ticket',
		'ActionProperty_MKB',
		'ActionProperty_OrgStructure',
		'ActionProperty_Organisation',
		'ActionProperty_Person',
		'ActionProperty_String',
		'ActionProperty_Time',
		'ActionProperty_rbFinance',
		'ActionProperty_rbReasonOfAbsence']

def deleteAction(aid):
    with connect() as conn:
        try:
	    tid = query(conn, 'select tissue_id from ActionTissue where action_id = %d' % aid)
	    execute(conn, 'delete from ActionTissue where action_id=%d' % aid)
	    if tid:
		delete(conn, 'Tissue', tid[0][0])
	    apids = query(conn, 'select id from ActionProperty where action_id=%d' % aid)
	    for row in apids:
		apid = row[0]
		delete(conn, 'ActionProperty', apid)
		for t in valueTables:
		    delete(conn, t, apid)
	    delete(conn, 'Action', aid)
	    conn.commit()
        except Exception as e:
            print "Exception %s" % e.message
            conn.rollback()
            raise e



