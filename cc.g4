grammar cc;

start       : (not | identifier | number | add | mult | equals | whitespace)+ EOF;
not         : NOT;
identifier  : IDENTIFIER;
number      : NUMBER;
add         : ADD;
mult        : MULT;
equals      : EQUALS;
whitespace  : WHITESPACE;


IDENTIFIER: [a-zA-Z_]+[a-zA-Z_0-9]*;
WHITESPACE: [\n]+;
NUMBER: [0-9]+;
ADD : '+';
MULT : '*';
NOT : '/';
EQUALS: '=';
