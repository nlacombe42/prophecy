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
    : literal
    | call
    ;

literal
    : INTEGER_LITERAL #integerLiteral
    | STRING_LITERAL #stringLiteral
    ;

INTEGER_LITERAL: [0-9]+;
STRING_LITERAL: '"' ( '\\"' | . )*? '"';

IDENTIFIER: [a-zA-Z][a-zA-Z0-9_]*;

NEWLINE: '\n' ' '*;
