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


file: statement+;

statement
    : call NEWLINE
    | variableDeclaration NEWLINE
    | foreach
    ;

call: expression '.' methodName=nonTypeIdentifier '(' arguments=expressionList? ')';

variableDeclaration: 'val' ' ' variableName=nonTypeIdentifier ' = ' initializer=expression;

foreach: 'foreach' ' ' variableName=nonTypeIdentifier ' ' 'in' ' ' expression statementBlock;

statementBlock: INDENT statement+ DEDENT;

expressionList: expression (', ' expression)*;

expression
    : literal #literalExpression
    | expression '.' methodName=nonTypeIdentifier '(' arguments=expressionList? ')' #selectionCallExpression
    | identifier=anyTypeIdentifier #identifierExpression
    ;

literal
    : INTEGER_LITERAL #integerLiteral
    | STRING_LITERAL #stringLiteral
    | '[' expressionList ']' #arrayLiteral
    ;

nonTypeIdentifier: NON_TYPE_IDENTIFIER | 'val' | 'foreach' | 'in';
anyTypeIdentifier: TYPE_IDENTIFIER | nonTypeIdentifier;

INTEGER_LITERAL: [0-9]+;
STRING_LITERAL: '\'' ( '\\\'' | ~'\n' )*? '\'';

TYPE_IDENTIFIER: [A-Z][a-zA-Z0-9]*;
NON_TYPE_IDENTIFIER: [a-z][a-zA-Z0-9]*;

NEWLINE: '\n' ('    ')*;

ONE_LINE_COMMENT: '#' (~'\n')* -> skip;
