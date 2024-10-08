grammar cc;

start       : (assignment | updates | def | siminputs | inputs | outputs | latches | COMMENT | WHITESPACE)* EOF;

// Top level:

updates: 'updates' COLON (expression)+;
siminputs:  'siminputs' COLON (IDENTIFIER EQUALS NUMBER)+;
inputs: 'inputs' COLON (expression)+;
outputs: 'outputs' COLON (expression)+;
def: DEF COLON IDENTIFIER '(' IDENTIFIER (',' IDENTIFIER)* ')' EQUALS (expression)+;
latches: 'latches' COLON (expression)+;
assignment: TYPES COLON expression;


// Expressions:
expression: IDENTIFIER EQUALS expression
            | NOT IDENTIFIER
            | expression AND expression
            | expression OR expression
            | IDENTIFIER '(' expression (',' expression)* ')'
            | IDENTIFIER;

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

