grammar Main;

program     : (functionDefinition | variableDeclaration)+;

functionDefinition
            : type Identifier '(' parameterList? ')' '{' statement* '}';

variableDeclaration
            : type Identifier ('=' expression)? ';';

parameterList
            : parameter (',' parameter)*;

parameter   : type Identifier;

type        : 'int' | 'float' | 'char' | 'void';

statement   : compoundStatement
            | expressionStatement
            | selectionStatement
            | iterationStatement
            | jumpStatement;

compoundStatement
            : '{' statement* '}';

expressionStatement
            : expression? ';';

selectionStatement
            : 'if' '(' expression ')' statement ('else' statement)?;

iterationStatement
            : 'while' '(' expression ')' statement
            | 'for' '(' expressionStatement? ';' expression? ';' expressionStatement? ')' statement;

jumpStatement
            : 'return' expression? ';';

expression  : assignmentExpression;

assignmentExpression
            : conditionalExpression
            | unaryExpression assignmentOperator assignmentExpression;

conditionalExpression
            : logicalOrExpression ('?' expression ':' conditionalExpression)?;

logicalOrExpression
            : logicalAndExpression ('||' logicalAndExpression)*;

logicalAndExpression
            : equalityExpression ('&&' equalityExpression)*;

equalityExpression
            : relationalExpression (('==' | '!=') relationalExpression)*;

relationalExpression
            : additiveExpression (('<' | '>' | '<=' | '>=') additiveExpression)*;

additiveExpression
            : multiplicativeExpression (('+' | '-') multiplicativeExpression)*;

multiplicativeExpression
            : unaryExpression (('*' | '/' | '%') unaryExpression)*;

unaryExpression
            : postfixExpression
            | ('++' | '--') unaryExpression
            | ('+' | '-' | '!' | '~') unaryExpression;

postfixExpression
            : primaryExpression
            | postfixExpression '[' expression ']'
            | postfixExpression '(' argumentList? ')'
            | postfixExpression '.' Identifier
            | postfixExpression '->' Identifier
            | postfixExpression '++'
            | postfixExpression '--';

primaryExpression
            : Identifier
            | Constant
            | StringLiteral
            | '(' expression ')';

argumentList
            : expression (',' expression)*;

assignmentOperator
            : '=' | '+=' | '-=' | '*=' | '/=' | '%=' | '&=' | '|=' | '^=' | '<<=' | '>>=';

Identifier  : [a-zA-Z_][a-zA-Z0-9_]*;
Constant    : IntegerConstant | FloatingConstant | CharacterConstant;
StringLiteral
            : '"' (EscapeSequence | ~('\\'|'"'))* '"';
IntegerConstant
            : DecimalConstant | OctalConstant | HexadecimalConstant;
DecimalConstant
            : Digit+;
OctalConstant
            : '0' [0-7]*;
HexadecimalConstant
            : '0' [xX] HexDigit+;
FloatingConstant
            : FractionalConstant ExponentPart?
            | Digit+ ExponentPart;
FractionalConstant
            : Digit* '.' Digit+
            | Digit+ '.';
ExponentPart
            : [eE] [+-]? Digit+;
CharacterConstant
            : '\'' (EscapeSequence | ~('\\'|'\'')) '\'';
EscapeSequence
            : '\\' [abfnrtv\\'\"] | OctalEscape | HexadecimalEscape;
OctalEscape
            : '\\' OctalDigit OctalDigit? OctalDigit?;
HexadecimalEscape
            : '\\' [xX] HexDigit+;
Digit       : [0-9];
HexDigit    : [0-9a-fA-F];
Oct
