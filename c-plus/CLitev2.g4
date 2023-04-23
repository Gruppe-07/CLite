grammar CLitev2;

    compilationUnit
            :   translationUnit? EOF;

    translationUnit
        :   externalDeclaration+
        ;

    externalDeclaration
        :   functionDefinition
        |   declaration
        |   ';' // stray ;
        ;

    declaration
        :   ('const')? typeSpecifier initDeclaratorList? ';'
        ;

    initDeclaratorList
        :   initDeclarator (',' initDeclarator)*
        ;

    initDeclarator
        :   declarator ('=' initializer)?
        ;

    declarator
        :   directDeclarator
        ;

    directDeclarator
        :   Identifier
        |   directDeclarator '(' identifierList? ')'
        |   directDeclarator '[' Identifier | Constant ']'
        ;

   identifierList
       :   Identifier (',' Identifier)*
       ;

       initializer
           :   assignmentStatement
           |   '{' initializerList ','? '}'
           ;

   initializerList
       :    initializer (',' initializer)*
       ;



   functionDefinition
            :   Function Identifier '(' parameterDeclaration? ')' compoundStatement
            ;

   compoundStatement
       :   '{' blockItemList? '}'
       ;

   blockItemList
       :   blockItem+
       ;

   blockItem
       :   statement
       |   declaration
       ;

   statement
       :   compoundStatement
       |   expressionStatement
       |   selectionStatement
       |   iterationStatement
       |   jumpStatement
       |   assignmentStatement
       ;

   expressionStatement
       :   expression? ';'
       ;

   assignmentStatement
       :    logicalOrExpression
       |    typeSpecifier Identifier assignmentOperator expression
       |    Identifier assignmentOperator expression
       |    DigitSequence
       ;

   assignmentOperator
        : '='
        ;

   unaryExpression
       :
       ( postfixExpression
       |   unaryOperator multiplicativeExpression
       )
       ;

   postfixExpression
       :
       (primaryExpression)
       ('++' | '--')?
       ;

   primaryExpression
       :   Identifier
       |   Constant
       |   StringLiteral+
       ;

   unaryOperator
       :   '!'
       ;

   multiplicativeExpression
           :   unaryExpression (('*'|'/'|'%') unaryExpression)*
           |   DigitSequence (('*'|'/'|'%') DigitSequence)*
           ;

   additiveExpression
       :   multiplicativeExpression (('+'|'-') multiplicativeExpression)*
       ;

    relationalExpression
        :   additiveExpression (('<'|'>'|'<='|'>=') additiveExpression)*
        ;

   equalityExpression
       :   relationalExpression (('=='| '!=') relationalExpression)*
       ;

    logicalAndExpression
        :   equalityExpression ('&&' equalityExpression)*
        ;

   logicalOrExpression
       :   logicalAndExpression ( '||' logicalAndExpression)*
       ;

   expression
       :   logicalOrExpression (',' logicalOrExpression)*
       |    DigitSequence
       ;

   jumpStatement
       :    'return' expression? ';'
       ;

   selectionStatement
       :   'if' '(' expression ')' compoundStatement ('else' compoundStatement)?
       ;

   iterationStatement
       :   While '(' expression ')' compoundStatement
       |   For '(' forCondition ')' compoundStatement
       |   For '(' typeSpecifier Identifier 'in' Identifier ')' compoundStatement
       ;

   forCondition
   	:   (forDeclaration | expression?) ';' forExpression? ';' forExpression?
   	;

   forDeclaration
       :   typeSpecifier initDeclaratorList?
       ;

   forExpression
       :   assignmentStatement (',' assignmentStatement)*
       ;

    parameterDeclaration
            :   typeSpecifier Identifier ('[' ']')?
            ;

    typeSpecifier
        :   'void'
        |   'char'
        |   'int'
        |   'double'
        |   'string'
        |   'tuple'
        |   'array'
        ;

    Char : 'char';
    Const : 'const';
    Double : 'double';
    Else : 'else';
    For : 'for';
    If : 'if';
    Int : 'int';
    Tuple : 'tuple';
    String : 'string';
    Array : 'array';
    Function : 'function';


    Return : 'return';
    Void : 'void';
    In : 'in';

    While : 'while';

    LeftParen : '(';
    RightParen : ')';
    LeftBracket : '[';
    RightBracket : ']';
    LeftBrace : '{';
    RightBrace : '}';

    Less : '<';
    LessEqual : '<=';
    Greater : '>';
    GreaterEqual : '>=';

    Plus : '+';
    PlusPlus : '++';
    Minus : '-';
    MinusMinus : '--';
    Star : '*';
    Div : '/';
    Mod : '%';

    AndAnd : '&&';
    OrOr : '||';
    Not : '!';

    Semi : ';';
    Comma : ',';

    Assign : '=';


    Equal : '==';
    NotEqual : '!=';

    Identifier
        :   IdentifierNondigit
            (   IdentifierNondigit
            |   Digit
            )*
        ;

    fragment
    IdentifierNondigit
        :   Nondigit
        ;

    StringLiteral
            :   '"' SCharSequence? '"'
            ;
    fragment
        SCharSequence
            :   SChar+
            ;

   fragment
       SChar
           :   ~["\\\r\n]
           |   EscapeSequence
           |   '\\\n'   // Added line
           |   '\\\r\n' // Added line
           ;

    fragment
    Nondigit
        :   [a-zA-Z_]
        ;

    fragment
    Digit
        :   [0-9]
        ;

   DigitSequence
       :   Digit+
       ;

   Constant
       :   IntegerConstant
       |   FloatingConstant
       |   CharacterConstant
       ;

    fragment
    IntegerConstant
        :   DecimalConstant
        ;

   fragment
   DecimalConstant
       :   NonzeroDigit Digit*
       ;

   fragment
   NonzeroDigit
       :   [1-9]
       ;

   fragment
   FloatingConstant
       :   FractionalConstant
       ;

   fragment
   FractionalConstant
       :   DigitSequence '.' DigitSequence
       ;

   fragment
   CharacterConstant
       :   '\'' CCharSequence '\''
       ;

   fragment
   CCharSequence
       :   CChar+
       ;

   fragment
   CChar
       :   ~['\\\r\n]
       |   EscapeSequence
       ;

   fragment
   EscapeSequence
       :   SimpleEscapeSequence
       ;

   fragment
   SimpleEscapeSequence
       :   '\\' ['"?abfnrtv\\]
       ;

    Whitespace
        :   [ \t]+
            -> skip
        ;

    Newline
        :   (   '\r' '\n'?
            |   '\n'
            )
            -> skip
        ;

    BlockComment
        :   '/*' .*? '*/'
            -> skip
        ;

    LineComment
        :   '//' ~[\r\n]*
            -> skip
        ;