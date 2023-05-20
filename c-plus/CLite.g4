grammar CLite;

    compilationUnit
            :   translationUnit? EOF
            ;

    expression
        :   assignmentExpression
        ;

    multiplicativeExpression
        :   unaryExpression
        |   multiplicativeExpression ('*'|'/'|'%') multiplicativeExpression
        ;

    additiveExpression
        :   multiplicativeExpression
        |   additiveExpression ('+' | '-') additiveExpression
        ;

    relationalExpression
        :   additiveExpression
        |   relationalExpression (('<'|'>'|'<='|'>=') relationalExpression)
        ;

    equalityExpression
        :   relationalExpression
        |   equalityExpression ('=='| '!=') equalityExpression
        ;

    logicalAndExpression
        :   equalityExpression
        |   logicalAndExpression '&&' logicalAndExpression
        ;

    logicalOrExpression
        :   logicalAndExpression
        |   logicalOrExpression  '||' logicalOrExpression
        ;


    assignmentExpression
        :   logicalOrExpression
        |   unaryExpression assignmentOperator assignmentExpression
        ;

    unaryExpression
        :
        (postfixExpression
        |   unaryOperator multiplicativeExpression
        )
        ;

    postfixExpression
        : parensExpression
        | functionCall
        | incrementDecrement
        ;

    parensExpression
        : '(' expression ')'
        ;

    functionCall
        : Identifier '(' expression? ')'
        ;
    incrementDecrement
        : (Identifier | Constant) ('++' | '--')?
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
        :   functionDefinition+
        ;

    functionDefinition
        :   typeSpecifier Identifier '(' parameterDeclaration? ')' compoundStatement
        ;

    typeSpecifier
        :   'int'
        |   'double'

        ;

    declaration
        :   ('const')? typeSpecifier Identifier '=' expression ';'
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
        :   expression ';'
        ;

    selectionStatement
        :   'if' '(' expression ')' compoundStatement ('else' compoundStatement)?
        ;

    iterationStatement
        :   While '(' expression ')' compoundStatement
        ;

    jumpStatement
        :   'return' expression? ';'
        ;



    Const : 'const';
    Function : 'function';
    Int : 'int';
    Double : 'double';
    String : 'string';
    If : 'if';
    Return : 'return';
    While : 'while';
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
        //|   CharacterConstant
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