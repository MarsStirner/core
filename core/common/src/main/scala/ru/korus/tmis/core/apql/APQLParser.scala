package ru.korus.tmis.core.apql


import scala.util.parsing.combinator._
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.combinator.syntactical.StdTokenParsers
import scala.util.parsing.json.Lexer

/**
 * Action & Property Query Language parser
 *
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 9/15/14
 * Time: 2:35 PM
 */
class APQLParser extends StdTokenParsers with PackratParsers {
  type Tokens = StdLexical
  val jsonLexer = new Lexer
  val lexical = new StdLexical
  lexical.delimiters ++= Seq(".", "(", ")", ",", " ")

  lazy val ifThenExpr: PackratParser[IfThenExpr] = ifLiteral ~ condition ~ thenLiteral ~ expr ^^ {
    case _if ~ cond ~ _then ~ expression => IfThenExpr(cond, expression)
  }

  lazy val condition: PackratParser[Condition] = acond | ocond

  lazy val acond: PackratParser[Condition] = "(" ~> andConditionList <~")" ^^ { case x => ANDCondition(x) }
  lazy val ocond: PackratParser[Condition] = "(" ~> orConditionList <~")" ^^ { case x => ORCondition(x) }

  lazy val andConditionList: PackratParser[List[Expr]] = repsep(expr, andLiteral)
  lazy val orConditionList: PackratParser[List[Expr]] = repsep(expr, orLiteral)

  lazy val orLiteral: PackratParser[Expr] = ident ^^ { case "OR" => OR }
  lazy val andLiteral: PackratParser[Expr] = ident ^^ { case "AND" => AND }
  lazy val ifLiteral: PackratParser[Expr] = ident ^^ { case "IF" => IF}
  lazy val thenLiteral: PackratParser[Expr] = ident ^^ { case "THEN" => THEN }

  lazy val expr: PackratParser[Expr] = method | number | string

  lazy val method: PackratParser[MethodCall] = expr.? ~ ".".? ~ ident ~ "(" ~ args ~ ")" ^^ {
    case expression ~ dot ~ name ~ "(" ~ arguments ~ ")" => MethodCall(expression, name, arguments)
  }

  lazy val args: PackratParser[List[Expr]] = repsep(expr, ",")

  lazy val number: PackratParser[NumericLiteral] = numericLit ^^ { case n => NumericLiteral(n) }
  lazy val string: PackratParser[StringLiteral] = stringLit ^^ { case n => StringLiteral(n) }

  def parse(source: String): ParseResult[IfThenExpr] = {
    val tokens = new lexical.Scanner(source)
    phrase(ifThenExpr)(tokens)
  }
}