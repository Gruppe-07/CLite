grammar CLite;

    compilationUnit
            :   translationUnit? EOF
            ;

    primaryExpression
        :   Identifier          #IdentifierExpr
        |   Constant            #ConstantExpr
        |   StringLiteral+      #StringLiteralExpr
        |   '(' expression ')' #parensExpr
        ;

    expression
        :   assignmentStatement (',' assignmentStatement)*
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


    assignmentStatement
        :   logicalOrExpression
        |   unaryExpression assignmentOperator assignmentStatement
        |   DigitSequence
        ;

    unaryExpression
        :
        (postfixExpression
        |   unaryOperator multiplicativeExpression
        )
        ;

    postfixExpression
        :
        primaryExpression
        ('[' expression ']'
        | '(' assignmentStatement? ')'
        | ('++' | '--')?)
        ;

    unaryOperator
        :   '!'
        ;

    assignmentOperator
        :   '='
        ;


    parameterDeclaration
          :   typeSpecifier Identifier
          ;


    translationUnit
        :   externalDeclaration+
        ;


    externalDeclaration
        :   functionDefinition
        |   declaration
        |   ';' // stray ;
        ;

    functionDefinition
        :   Function Identifier '(' parameterDeclaration? ')' compoundStatement
        ;

    typeSpecifier
        :   'void'
        |   'char'
        |   'int'
        |   'double'
        |   'string'
        |   'array'
        |   'tuple'
        ;

    declaration
        :   ('const')? typeSpecifier Identifier ('=' initializer)? ';'
        ;


    initializer
        :   assignmentStatement
        |   '{' initializerList ','? '}'
        ;

    initializerList
        :   initializer (',' initializer)*
        ;


    specifierQualifierList
        :   (typeSpecifier| 'const' ) specifierQualifierList?
        ;

    statement
        :   compoundStatement
        |   expressionStatement
        |   selectionStatement
        |   iterationStatement
        |   jumpStatement
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

    expressionStatement
        :   expression? ';'
        ;

    selectionStatement
        :   'if' '(' expression ')' compoundStatement ('else' compoundStatement)?
        ;

    iterationStatement
        :   While '(' expression ')' compoundStatement
        |   For '(' forCondition ')' compoundStatement
        |   Foreach '(' typeSpecifier Identifier 'in' Identifier ')' compoundStatement
        ;

    jumpStatement
        :   'return' expression?
        ';'
        ;

    forCondition
    	:   declaration relationalExpression ';' postfixExpression
    	;



    Const : 'const';
    Function : 'function';
    Int : 'int';
    Double : 'double';
    Tuple : 'tuple';
    Array : 'array';
    String : 'string';
    If : 'if';
    Return : 'return';
    While : 'while';
    For   : 'for';
    Foreach   : 'foreach';
    Else : 'else';

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

    Not : '!';
    AndAnd : '&&';
    OrOr : '||';

    Semi : ';';
    Comma : ',';

    Assign : '=';

    Equal : '==';
    NotEqual : '!=';

    Dot : '.';

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

    Constant
        :   IntegerConstant
        |   FloatingConstant
        |   CharacterConstant
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
        :   DecimalFloatingConstant
        ;

    fragment
    DecimalFloatingConstant
        :   FractionalConstant
        |   DigitSequence
        ;

    fragment
    FractionalConstant
        :   DigitSequence? '.' DigitSequence
        |   DigitSequence '.'
        ;

    DigitSequence
        :   Digit+
        ;

    BlockComment
        :   '/*' .*? '*/'
            -> skip
        ;

    LineComment
        :   '//' ~[\r\n]*
            -> skip
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