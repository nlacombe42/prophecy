grammar Prophecy;

//==========Rules==========
file: classdef EOF ;

//Class definition
classdef: CLASS name=ID (EXTENDS superClassName=ID)? defblock ;

//A block containing definition statements.
defblock: '{' defstat+ '}' ;

//Definition statement
defstat: modifier? (fielddef | methoddef) ;

modifier: static_=STATIC ;

//Field definition
fielddef: type name=ID ';' ;

//Method definition
methoddef: type? name=ID '(' paramlist? ')' block ;

//Parameter list
paramlist: param (',' param)* ;

//Parameter
param: type name=ID ;

block: '{' stmt* '}' ;

//Statement
stmt
    : block #blockStmt
    | vardecl #varDeclStmt
    | IF '(' cond=expr ')' stmt (ELSE stmt )? #ifStmt
    | WHILE '(' expr ')' stmt #whileStmt
    | RETURN value=expr? ';' #returnStmt
    | lhs '=' rhs=expr ';' #assignmentStmt
    | call ';' #callStmt
    ;

//Left hand side (of an assigment).
lhs: selexpr ;

//Variable declaration
vardecl
    :   type name=ID ('=' init=expr)? ';' #regVardecl
    |   type '[]' name=ID ('=' init=expr)? ';' #arrayVardecl //Array declaration
    ;
    
//Expression list
exprlist: expr (',' expr)* ;

//----------Expressions----------

expr:
    //Binary operators (by precedence)
      lhs '=' expr #assignmentExpr
    | left=expr '||' right=expr #logicOrExpr
    | left=expr '&&' right=expr #logicAndExpr
    | left=expr '|' right=expr #bitOrExpr
    | left=expr '^' right=expr #bitXorExpr
    | left=expr '&' right=expr #bitAndExpr
    | left=expr operator=('==' | '!=') right=expr #equalityExpr
    | left=expr operator=('<' | '>' | '<=' | '>=') right=expr #relationExpr
    | left=expr operator=('<<' | '>>') right=expr #shiftExpr
    | left=expr operator=('*' | '/') right=expr #mulExpr
    | left=expr operator=('+' | '-') right=expr #addExpr
    | '(' type ')' expr #castExpr
    | operator=('++' |'--' | '+' | '-' | '!' | '~') expr #prefixExpr
    | selexpr operator=('++' | '--') #postfixExpr
    | selexpr #selectorExpr
    ;

selexpr:
	     selexpr '.' ID #memberAccessSelExpr
	   | selexpr '[' expr ']' #arrayAccessSelExpr
	   | selexpr '(' exprlist? ')' #callSelExpr //Note: same thing as call.
	   | primary #primarySelExpr
	   ;
	   
call: selexpr '(' exprlist? ')';

primary
    : ID #IdPrimary
    | THIS #thisPrimary
    | INTLIT #intlitPrimary
    | FLOATLIT #floatlitPrimary
    | CHARLIT #charlitPrimary
    | STRINGLIT #stringlitPrimary
    | TRUE #truePrimary
    | FALSE #falsePrimary
    | '(' expr ')' #bracketPrimary
    ;

type
    : primitiveType
    | ID
    ;

primitiveType
    : FLOAT
    | INT
    | CHAR
    | BOOL
    | VOID
    ;

//==========Tokens==========

//----------Keywords----------

CLASS: 'class' ;
EXTENDS: 'extends' ;
THIS: 'this' ;
STATIC: 'static' ;
IF: 'if' ;
WHILE: 'while' ;
ELSE: 'else' ;
RETURN: 'return' ;

TRUE: 'true' ;
FALSE: 'false' ;

FLOAT: 'float' ;
INT: 'int' ;
CHAR: 'char' ;
BOOL: 'bool' ;
VOID: 'void';

//Identifier
ID: [a-zA-Z][a-zA-Z0-9_]* ;

//----------Literals----------

//String literal
STRINGLIT: '"' ( ~( '\r' | '\n' | '"' ) )* '"' ;

//Integer literal
INTLIT: [0-9]+ ;
/* Cannot put a '-' in front because "f()-5" would be understood as "f()" "-5"
 * instead of "f()" "-" "5".
 */

//Floating point number literal
FLOATLIT: '-'? [0-9]+ '.' [0-9]+ ;

//Character literal
CHARLIT: '\'' ~('\r' | '\n' | '\'') '\'' ;

//Spaces (skip them)
WS: [ \t\r\n]+ -> skip ;

//----------Comments----------

//Single line comment
SL_COMMENT: '//' ~('\r'|'\n')* '\r'? '\n' -> skip ;

//Multiline comment
ML_COMMENT: '/*' .*? '*/' -> skip ;
