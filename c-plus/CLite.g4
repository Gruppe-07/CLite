grammar CLite;

    compilationUnit
            :   translationUnit? EOF
            ;

    primaryExpression
        :   Identifier
        |   Constant
        |   StringLiteral+
        |   '(' expression ')';

    expression
        :   assignmentExpression (',' assignmentExpression)*
        ;


    castExpression
        :   '(' typeName ')' castExpression
        |    unaryExpression
        |   DigitSequence // for
        ;

    multiplicativeExpression
        :   castExpression (('*'|'/'|'%') castExpression)*
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

    constantExpression
        :   logicalOrExpression
        ;


    assignmentExpression
        :   logicalOrExpression
        |   unaryExpression assignmentOperator assignmentExpression
        |   DigitSequence
        ;

    unaryExpression
        :
        ('++' |  '--' |  'sizeof')*
        (postfixExpression
        |   unaryOperator castExpression
        )
        ;



    postfixExpression
        :
        (   primaryExpression
        |   '(' typeName ')' '{' initializerList ','? '}'
        )
        ('[' expression ']'
        | ('++' | '--')
        )*
        ;

    unaryOperator
        :   '&' | '*' | '+' | '-' | '~' | '!'
        ;


    assignmentOperator
        :   '='
        ;

    declarator
        :   pointer? directDeclarator
        ;

    pointer
        :  ('*' typeQualifierList?)+ // ^ - Blocks language extension
        ;

    typeQualifierList
        :   typeQualifier+
        ;

    directDeclarator
        :   Identifier
        |   '(' declarator ')'
        |   directDeclarator '[' typeQualifierList? assignmentExpression? ']'
        |   directDeclarator '[' 'static' typeQualifierList? assignmentExpression ']'
        |   directDeclarator '[' typeQualifierList 'static' assignmentExpression ']'
        |   directDeclarator '[' typeQualifierList? '*' ']'
        |   directDeclarator '(' parameterTypeList ')'
        |   directDeclarator '(' identifierList? ')'
        ;

    identifierList
        :   Identifier (',' Identifier)*
        ;

    parameterTypeList
        :   parameterList (',' '...')?
        ;

    parameterList
        :   parameterDeclaration
        ;


    parameterDeclaration
          :   declarationSpecifiers declarator
          |   declarationSpecifiers2 abstractDeclarator?
          ;

    declarationSpecifiers2
        :   declarationSpecifier+
        ;

    abstractDeclarator
        :   pointer
        |   pointer? directAbstractDeclarator
        ;

    directAbstractDeclarator
        :   '(' abstractDeclarator ')'
        |   '[' typeQualifierList? assignmentExpression? ']'
        |   '[' 'static' typeQualifierList? assignmentExpression ']'
        |   '[' typeQualifierList 'static' assignmentExpression ']'
        |   '[' '*' ']'
        |   '(' parameterTypeList? ')'
        |   directAbstractDeclarator '[' typeQualifierList? assignmentExpression? ']'
        |   directAbstractDeclarator '[' 'static' typeQualifierList? assignmentExpression ']'
        |   directAbstractDeclarator '[' typeQualifierList 'static' assignmentExpression ']'
        |   directAbstractDeclarator '[' '*' ']'
        |   directAbstractDeclarator '(' parameterTypeList? ')'
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
        :   'function' declarator declarationList? compoundStatement
        ;

    typeSpecifier
        :   'void'
        |   'char'
        |   'int'
        |   'double'
        |   'string'
        |   structSpecifier
        |   arraySpecifier
        |   tupleSpecifier
        ;

    structSpecifier
        :   'struct' Identifier '{' structDeclarationList '}'
        ;

    structDeclarationList
        :   structDeclaration+
        ;

    structDeclaration // The first two rules have priority order and cannot be simplified to one expression.
        :   specifierQualifierList structDeclaratorList ';'
        |   specifierQualifierList ';'
        ;

    structDeclaratorList
        :   structDeclarator (',' structDeclarator)*
        ;

    structDeclarator
        :   declarator
        ;

    arraySpecifier
        :   'array' Identifier '=' '{' initializerList '}'
        ;

    tupleSpecifier
        :   'tuple' Identifier '{' tupleFields? '}'
        ;

    tupleFields
        :   tupleField (',' tupleField)*
        ;

    tupleField
        :   declarationSpecifiers declarator
        ;

    declaration
        :   declarationSpecifiers initDeclaratorList? ';'
        ;

    initDeclaratorList
        :   initDeclarator (',' initDeclarator)*
        ;

    initDeclarator
        :   declarator ('=' initializer)?
        ;

    initializer
        :   assignmentExpression
        |   '{' initializerList ','? '}'
        ;

    initializerList
        :   designation? initializer (',' designation? initializer)*
        ;

    designation
        :   designatorList '='
        ;

    designatorList
        :   designator+
        ;

    designator
        :   '[' constantExpression ']'
        |   '.' Identifier
        ;

    declarationSpecifiers
        :   declarationSpecifier+
        ;

    declarationSpecifier
        :   typeSpecifier
        |   typeQualifier
        ;

    declarationList
        :   declaration+
        ;

    typeName
        :   specifierQualifierList
        ;

    specifierQualifierList
        :   (typeSpecifier| typeQualifier) specifierQualifierList?
        ;

    typeQualifier
        :   'const'
        ;

    statement
        :   compoundStatement
        |   expressionStatement
        |   selectionStatement
        |   iterationStatement
        | jumpStatement
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
        :   'if' '(' expression ')' statement ('else' statement)?
        ;

    iterationStatement
        :   While '(' expression ')' statement
        |   For '(' forCondition ')' statement
        |   For '(' typeSpecifier Identifier 'in' Identifier ')' statement
        ;

    jumpStatement
        :   'return' expression?
        ';'
        ;

    forCondition
    	:   (forDeclaration | expression?) ';' forExpression? ';' forExpression?
    	;

    forDeclaration
        :   declarationSpecifiers initDeclaratorList?
        ;

    forExpression
        :   assignmentExpression (',' assignmentExpression)*
        ;

    Function : 'function';
    Int : 'int';
    Double : 'double';
    Tuple : 'tuple';
    Array : 'array';
    Struct : 'struct';
    String : 'String';
    If : 'if';
    Return : 'return';
    While : 'while';
    For   : 'for';
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

    And : 'and';
    Or : 'or';
    Not : 'not';

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
        //|   EnumerationConstant
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