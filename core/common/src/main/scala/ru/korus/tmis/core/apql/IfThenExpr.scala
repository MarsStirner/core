package ru.korus.tmis.core.apql

import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.combinator.syntactical.StdTokenParsers

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 9/15/14
 * Time: 2:54 PM
 */
case class IfThenExpr(condition: Condition, value: Expr)

sealed trait Condition
case class ORCondition(expressions: List[Expr]) extends Condition
case class ANDCondition(expressions: List[Expr]) extends Condition

sealed trait Expr
case class MethodCall(expr: Option[Expr], name: String, args: List[Expr]) extends Expr
case class NumericLiteral(value: String) extends Expr
case class StringLiteral(value: String) extends Expr
case class BooleanLiteral(value: Boolean) extends Expr
object OR extends Expr
object AND extends Expr
object IF extends Expr
object THEN extends Expr