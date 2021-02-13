grammar ProphecyV2;

tokens { INDENT, DEDENT }

@lexer::header {
  import com.yuvalshavit.antlr4.DenterHelper;
}

@lexer::members {
  private final DenterHelper denter = DenterHelper.builder()
    .nl(NEWLINE)
    .indent(ProphecyV2Parser.INDENT)
    .dedent(ProphecyV2Parser.DEDENT)
    .pullToken(ProphecyV2Lexer.super::nextToken);

  @Override
  public Token nextToken() {
    return denter.nextToken();
  }
}

file: statement*;

statement
    : call NEWLINE
    ;

call: methodName=IDENTIFIER '(' arguments=exprlist? ')';

exprlist: expression (', ' expression)*;

expression
    : left=expression operator=('*' | '/') right=expression #multiplicationExpression
    | left=expression operator=('+' | '-') right=expression #additionExpression
    | literal #literalExpression
    ;

literal
    : INTEGER_LITERAL #integerLiteral
    ;

INTEGER_LITERAL: [0-9]+;

IDENTIFIER: [a-zA-Z][a-zA-Z0-9_]*;

NEWLINE: ('\r'? '\n' ' '*);
