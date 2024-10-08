grammar cc;

start       : (assignment | updates | siminputs | inputs | outputs | latches | COMMENT)* EOF;

// Top level:

updates: 'updates' COLON (updatesExp)+;
siminputs:  'siminputs' COLON (siminputExp)+;
inputs: 'inputs' COLON (IDENTIFIER)+;
outputs: 'outputs' COLON (IDENTIFIER)+;
latches: 'latches' COLON (IDENTIFIER)+;
assignment: TYPES COLON IDENTIFIER;

// Expressions:
siminputExp: IDENTIFIER EQUALS NUMBER;
updatesExp: IDENTIFIER EQUALS updatesExp
            | NOT IDENTIFIER
            | updatesExp AND updatesExp
            | updatesExp OR updatesExp
            | IDENTIFIER;

WHITESPACE: [ \n\t\r]+ -> skip;
TYPES: 'hardware' | 'inputs' | 'outputs' | 'latches' | 'updates' | 'siminputs';
IDENTIFIER: [a-zA-Z_]+[a-zA-Z_0-9’]*;
NUMBER: [0-9]+;
AND: '*';
OR: '+';
NOT: '/';
EQUALS: '=';
COLON: ':';
COMMENT: ('//' ~[\n]* | '/*' .*? '*/') -> skip;

