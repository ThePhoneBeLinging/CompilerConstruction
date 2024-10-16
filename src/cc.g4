grammar cc;

start       : (hardware | updates | definitions | siminputs | inputs | outputs | latches | COMMENT | WHITESPACE)*;

// Top level:

updates: 'updates' COLON (updateInputExp)+ # UpdatesContext;
siminputs:  'siminputs' COLON (siminputExp)+ #SimnputContext;
inputs: 'inputs' COLON (expression)+ # InputsContext;
outputs: 'outputs' COLON (expression)+ # OutputsContext;
def: DEF COLON funcName=IDENTIFIER '(' ident1=IDENTIFIER (',' ident2=IDENTIFIER)* ')' EQUALS (implicitAndAbleExpression)+ # DefContext;
latches: 'latches' COLON (expression)+ # LathesContext;
hardware: 'hardware' COLON name=IDENTIFIER # HardwareContext;
definitions: def+ # MultipleDefinitions;

siminputExp: exp=expression op=EQUALS num=NUMBER # siminputexpression;
updateInputExp: ident=IDENTIFIER op=EQUALS exp=implicitAndAbleExpression #updateInputExpress;
// Expressions:
expression:  IDENTIFIER EQUALS expression #IdentEqExp
            | IDENTIFIER EQUALS NUMBER #IdentEqNum
            | NOT IDENTIFIER # NotExp
            | exp1 = expression (AND) exp2 = expression # ANDExp
            | exp1=expression OR exp2=expression # ORExp
            | IDENTIFIER '(' exp1=expression (',' exp2=expression)* ')' # DEFINITION
            | exp1=expression EQUALS exp2=expression # ExpEQExp
            | '(' expression ')' #expressionInParenthesis
            | IDENTIFIER #Variable
            ;
implicitAndAbleExpression:
                expression #simpleExpressionVisit
                | exp1=implicitAndAbleExpression (NOT? ident=IDENTIFIER)+                      #ImplicitAndExp
                | exp1=expression exp2=expression #ImplicitWithImplicit
                ;
DEF: 'def';
WHITESPACE: [ \n\t\r]+ -> skip;
TYPES: 'hardware' | 'inputs' | 'outputs' | 'latches' | 'updates' | 'siminputs';
NUMBER: [0-9]+;
AND: '*';
OR: '+';
NOT: '/';
EQUALS: '=';
COLON: ':';
COMMENT: ('//' ~[\n]* | '/*' .*? '*/') -> skip;
IDENTIFIER: [a-zA-Z_]+[a-zA-Z_0-9’']*;

