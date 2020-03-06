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
    : '[!--' .*? '--]'
    ;

BLOCK_ESCAPE
    : '[[--' .*? '--]]'
    ;

SEA_WS
    :  (' '|'\t'|'\r'? '\n')+
    ;

BLOCK_OPEN
    : '[' -> pushMode(BLOCK)
    ;

TEXT
    : ~'['+
    ;

mode BLOCK;

BLOCK_CLOSE
    : ']' -> popMode
    ;

fragment
BLOCK_ELSE_NAME
    : 'elseif'
    | 'else'
    ;

BLOCK_ELSE
    : BLOCK_ELSE_NAME
    ;

fragment
BLOCK_CTX_SET_NAME
    : 'ctx:set'
    ;

BLOCK_CTX_SET
    : BLOCK_CTX_SET_NAME
    ;

fragment
BLOCK_CTX_NAME
    : 'ctx:' BLOCK_ATTRIBUTE_NAME
    ;

BLOCK_CTX
    : BLOCK_CTX_NAME
    ;

fragment
BLOCK_REF_NAME
    : 'ref:template'
    | 'ref:placeholder'
    ;

BLOCK_REF
    : BLOCK_REF_NAME
    ;

BLOCK_NAME
    : 'template'
    | 'placeholder'
    | 'if'
    | 'for'
    | 'for:index'
    ;

BLOCK_ATTRIBUTE_NAME
    : [a-zA-Z]+[a-zA-Z0-9]*( '.' [a-zA-Z0-9]*)*
    ;

BLOCK_WHITESPACE
    : [ \t\r\n] -> skip
    ;

BLOCK_SLASH_CLOSE
    : '/]' -> popMode
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

mode ATTVALUE;

ATTVALUE_VALUE
    : [ ]* ATTRIBUTE -> popMode
    ;

ATTRIBUTE
    : DOUBLE_QUOTE_STRING
    ;

fragment DOUBLE_QUOTE_STRING
    : '"' ~[<"]* '"'
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