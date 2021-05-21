grammar Prophecy;

tokens { INDENT, DEDENT }

@lexer::header {
  import com.yuvalshavit.antlr4.DenterHelper;
}

@lexer::members {
  private final DenterHelper denter = DenterHelper.builder()
    .nl(NEWLINE)
    .indent(ProphecyParser.INDENT)
    .dedent(ProphecyParser.DEDENT)
    .pullToken(ProphecyLexer.super::nextToken);

  @Override
  public Token nextToken() {
    return denter.nextToken();
  }
}


file: statement*;

statement
    : call NEWLINE
    | variableDeclaration NEWLINE
    ;

call: methodName=nonTypeIdentifier '(' arguments=expressionList? ')';

expressionList: expression (', ' expression)*;

variableDeclaration: 'val' ' ' variableName=nonTypeIdentifier ' = ' initializer=expression;

expression
    : literal #literalExpression
    | call #callExpression
    | expression '.' call #selectionCallExpression
    | identifier=nonTypeIdentifier #identifierExpression
    ;

literal
    : INTEGER_LITERAL #integerLiteral
    | STRING_LITERAL #stringLiteral
    | '[' expressionList ']' #arrayLiteral
    ;

nonTypeIdentifier: (NON_TYPE_IDENTIFIER | 'val');

INTEGER_LITERAL: [0-9]+;
STRING_LITERAL: '"' ( '\\"' | ~('\r' | '\n') )*? '"';

TYPE_IDENTIFIER: [A-Z][a-zA-Z0-9]*;
NON_TYPE_IDENTIFIER: [a-z][a-zA-Z0-9]*;

NEWLINE: '\n' ' '*;
