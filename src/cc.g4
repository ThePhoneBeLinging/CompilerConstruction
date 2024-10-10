grammar cc;

start       : (hardware | updates | def | siminputs | inputs | outputs | latches | COMMENT | WHITESPACE)*;

// Top level:

updates: 'updates' COLON (expression)+ # UpdatesContext;
siminputs:  'siminputs' COLON (expression)+ #SimnputContext;
inputs: 'inputs' COLON (expression)+ # InputsContext;
outputs: 'outputs' COLON (expression)+ # OutputsContext;
def: DEF COLON IDENTIFIER '(' ident1=IDENTIFIER (',' ident2=IDENTIFIER)* ')' EQUALS (expression)+ # DefContext;
latches: 'latches' COLON (expression)+ # LathesContext;
hardware: 'hardware' COLON name=IDENTIFIER # HardwareContext;

// Expressions:
expression: IDENTIFIER EQUALS expression #IdentEqExp
            | IDENTIFIER EQUALS NUMBER #IdentEqNum
            | NOT IDENTIFIER # NotExp
            | exp1 = expression AND exp2 = expression # ANDExp
            | exp1=expression OR exp2=expression # ORExp
            | IDENTIFIER '(' expression (',' expression)* ')' # DEFINITION
            | exp1=expression EQUALS exp2=expression # ExpEQExp
            | IDENTIFIER # Variable;

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
IDENTIFIER: [a-zA-Z_]+[a-zA-Z_0-9’]*;

