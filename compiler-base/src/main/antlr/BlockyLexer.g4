/*
 * Copyright 2019 Donesky, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
lexer grammar BlockyLexer;

BLOCK_COMMENT
    : '{!--' .*? '--}'
    ;

SEA_WS
    :  (' '|'\t'|'\r'? '\n')+
    ;

BLOCK_OPEN
    : '{' -> pushMode(BLOCK)
    ;

TEXT
    : ~'{'+
    ;

mode BLOCK;

BLOCK_CLOSE
    : '}' -> popMode
    ;

fragment
BLOCK_ELSE_NAME
    : 'elseif'
    | 'else'
    ;

BLOCK_ELSE
    : BLOCK_ELSE_NAME
    ;

BLOCK_NAME
    : BLOCK_NameStartChar BLOCK_NameChar*
    ;

BLOCK_WHITESPACE
    : [ \t\r\n] -> skip
    ;

BLOCK_SLASH_CLOSE
    : '/}' -> popMode
    ;

BLOCK_SLASH
    : '/'
    ;

BLOCK_EQUALS
    : '=' -> pushMode(ATTVALUE)
    ;

BLOCK_EXPRESSION_OPEN
    : '[' -> pushMode(EXPRESSION)
    ;

fragment
DIGIT
    : [0-9]
    ;

fragment
BLOCK_NameChar
    : BLOCK_NameStartChar
    | '-'
    | '_'
    | '.'
    | DIGIT
    |   '\u00B7'
    |   '\u0300'..'\u036F'
    |   '\u203F'..'\u2040'
    ;

fragment
BLOCK_NameStartChar
    :   [:a-zA-Z]
    |   '\u2070'..'\u218F'
    |   '\u2C00'..'\u2FEF'
    |   '\u3001'..'\uD7FF'
    |   '\uF900'..'\uFDCF'
    |   '\uFDF0'..'\uFFFD'
    ;

mode ATTVALUE;

ATTVALUE_VALUE
    : [ ]* ATTRIBUTE -> popMode
    ;

ATTRIBUTE
    : DOUBLE_QUOTE_STRING
    | SINGLE_QUOTE_STRING
    ;

fragment DOUBLE_QUOTE_STRING
    : '"' ~[<"]* '"'
    ;
fragment SINGLE_QUOTE_STRING
    : '\'' ~[<']* '\''
    ;

mode EXPRESSION;

BLOCK_EXPRESSION_CLOSE
    : ']' -> popMode
    ;

EXPRESSION_WHITESPACE
    : [ \t\r\n] -> skip
    ;

EXPRESION_GROUP_OPEN
    : '('
    ;

EXPRESION_GROUP_CLOSE
    : ')'
    ;

EXPRESSION_NEGATIVE
    : '!'
    ;

EXPRESSION_LOGIC
    : '&&'
    | '||'
    ;

EXPRESSION_OPERATOR
    : '++'
    | '--'
    | '+'
    | '-'
    | '*'
    | '/'
    | EXPRESSION_NEGATIVE
    | '<'
    | '>'
    | '<='
    | '>='
    | '=='
    | '!='
    ;

EXPRESSION_DOT
    : '.'
    ;

EXPRESSION_NULL
    : 'null'
    ;

EXPRESSION_BOOLEAN
    : 'true'
    | 'false'
    ;

EXPRESSION_NUMBER
    : INTEGER '.' [0-9]* EXPONENT?
    | '.' [0-9]+ EXPONENT?
    | INTEGER EXPONENT?
    ;

EXPRESSION_STRING
    : '"' ~[<"]* '"'
    ;

EXPRESSION_VARIABLE_NAME
    : [a-zA-Z]+[a-zA-Z0-9]*( '.' [a-zA-Z0-9]*)*
    ;

fragment EXPONENT
    : [eE] [+-]? [0-9]+
    ;

fragment INTEGER
    : '0'
    | [1-9] [0-9]*
    ;