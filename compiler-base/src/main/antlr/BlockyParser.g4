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
parser grammar BlockyParser;

options {
    tokenVocab=BlockyLexer;
}

template
    : SEA_WS* blocks*
    ;

blocks
    : blockMisc* block blockMisc*
    ;

block
    : BLOCK_OPEN blockName blockAttribute* blockExpression* BLOCK_CLOSE blockContent BLOCK_OPEN BLOCK_SLASH blockName BLOCK_CLOSE
    | BLOCK_OPEN blockName blockAttribute* BLOCK_CLOSE
    ;

blockElse
    : BLOCK_OPEN blockElseName blockExpression* BLOCK_CLOSE blockContent
    ;

blockContent
    : blockText? ((blockElse | block | BLOCK_COMMENT) blockText?)*
    ;

blockAttribute
    : blockAttributeName BLOCK_EQUALS blockAttributeValue
    | blockAttributeName
    ;

blockExpression
    : BLOCK_EXPRESSION_OPEN expression BLOCK_EXPRESSION_CLOSE
    ;

expression
    : EXPRESSION_NEGATIVE expression
    | valueExpression
    | expression logicExpression expression
    | expressionGroup
    ;

expressionGroup
    : EXPRESION_GROUP_OPEN expression EXPRESION_GROUP_CLOSE
    ;

valueExpression
    : value EXPRESSION_OPERATOR value logicExpression expression
    | value EXPRESSION_OPERATOR value
    ;

logicExpression
    : EXPRESSION_LOGIC
    ;

blockAttributeName
    : BLOCK_NAME
    ;

blockAttributeValue
    : ATTVALUE_VALUE
    ;

blockName
    : BLOCK_NAME
    ;

blockElseName
    : BLOCK_ELSE
    ;

blockText
    : TEXT
    | SEA_WS
    ;

blockMisc
    : BLOCK_COMMENT
    | SEA_WS
    ;

value
    : identifierName
    | literal
    ;

identifierName
    : EXPRESSION_VARIABLE_NAME
    ;

literal
    : EXPRESSION_NULL
    | EXPRESSION_STRING
    | EXPRESSION_NUMBER
    | EXPRESSION_BOOLEAN
    ;