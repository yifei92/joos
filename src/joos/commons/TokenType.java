package joos.commons;

public enum TokenType {
	// Identifier
	IDENTIFIER,
	// Keywords
	ABSTRACT,
	BOOLEAN,
	BYTE,
	CHAR,
	CLASS,
	ELSE,
	EXTENDS,
	FINAL,
	FOR,
	IF,
	IMPLEMENTS,
	IMPORT,
	INSTANCEOF,
	INT,
	INTERFACE,
	NATIVE,
	NEW,
	NULL,
	PACKAGE,
	// PRIVATE,
	PROTECTED,
	PUBLIC,
	RETURN,
	SHORT,
	STATIC,
	// SUPER,
	THIS,
	VOID,
	WHILE,
	// CATCH,
	// TRY,
	// FINALLY,
	// THROW,
	// THROWS,
	// CONTINUE,
	// BREAK,
	// DO,
	// CASE,
	// DEFAULT,
	// SWITCH,
	// SYNCHRONIZED,
	// TRANSIENT,
	// VOLATILE,
	// FLOAT,
	// DOUBLE,
	// LONG,
	// Symbols
	ASSIGN,
	// BITWISE_AND,
	// BITWISE_NOT,
	// BITWISE_OR,
	BOOL_OP_AND,
	BOOL_OP_OR,
	ESCAPE,
	CLOSE_BR,
	CLOSE_CBR,
	CLOSE_SBR,
	COMMENT_JAVADOC,
	COMMENT_MULTI_LINE,
	COMMENT_SINGLE_LINE,
	COMP_EQ,
	COMP_GREATER_THAN,
	COMP_GREATER_THAN_EQ,
	COMP_LESS_THAN,
	COMP_LESS_THAN_EQ,
	COMP_NOT_EQ,
	DOT,
	OP_DIV,
	OP_MINUS,
	OP_PLUS,
	OP_REMAINDER,
	OPEN_BR,
	OPEN_CBR,
	OPEN_SBR,
	SEMICOLON,
	STAR,
	COLON,
	COMMA,
	// QUESTION_MARK,
	// BITWISE_XOR,
	// OP_LEFT_SHIFT,
	// OP_RIGHT_SHIFT,
	// OP_UNSIGNED_RIGHT_SHIFT,
	BOOL_OP_NOT,
	// ASSIGN_DECREMENT,
	// ASSIGN_INCREMENT,
	// Literals
	BOOLEAN_LITERAL_TRUE,
	BOOLEAN_LITERAL_FALSE,
	SINGLE_QUOTE,
	CHAR_LITERAL, // consists of a single char surrounded by single quotes
	INTEGER_LITERAL,
	DOUBLE_QUOTE,
	STRING_LITERAL, // consists of a string of characters surrounded by double quotes
	NULL_LITERAL,
	// FLOATING_POINT_LITERAL,
	// Space
	SPACE,
	EOF,
	NEW_LINE,
	TAB,
	// Nonterminals
	GOAL,
	LITERAL,
	STRING_LITERAL_WITH_QUOTES,
	STRING_LITERAL_WITH_ESCAPES_OPT,
	STRING_LITERAL_WITH_ESCAPES,
	CHAR_LITERAL_WITH_QUOTES,
	BOOLEAN_LITERAL,
	TYPE,
	PRIMITIVE_TYPE,
	NUMERIC_TYPE,
	INTEGRAL_TYPE,
	// FLOATING_POINT_TYPE,
	REFERENCE_TYPE,
	CLASS_OR_INTERFACE_TYPE,
	CLASS_TYPE,
	INTERFACE_TYPE,
	ARRAY_TYPE,
	NAME,
	SIMPLE_NAME,
	QUALIFIED_NAME,
	PACKAGE_DECLARATION_OPT,
	IMPORT_DECLARATIONS_OPT,
	TYPE_DECLARATIONS_OPT,
	COMPILATION_UNIT,
	IMPORT_DECLARATIONS,
	TYPE_DECLARATIONS,
	PACKAGE_DECLARATION,
	IMPORT_DECLARATION,
	SINGLE_TYPE_IMPORT_DECLARATION,
	TYPE_IMPORT_ON_DEMAND_DECLARATION,
	TYPE_DECLARATION,
	MODIFIERS,
	MODIFIER,
	MODIFIERS_OPT,
	SUPER_CLAUSE_OPT,
	INTERFACES_OPT,
	CLASS_DECLARATION,
	SUPER_CLAUSE,
	INTERFACES,
	INTERFACE_TYPE_LIST,
	CLASS_BODY_DECLARATIONS_OPT,
	CLASS_BODY,
	CLASS_BODY_DECLARATIONS,
	CLASS_BODY_DECLARATION,
	CLASS_MEMBER_DECLARATION,
	FIELD_DECLARATION,
	VARIABLE_DECLARATORS,
	VARIABLE_DECLARATOR,
	// VARIABLE_DECLARATOR_ID,
	VARIABLE_INITIALIZER,
	METHOD_DECLARATION,
	// THROWS_CLAUSE_OPT,
	METHOD_HEADER,
	FORMAL_PARAMETER_LIST_OPT,
	METHOD_DECLARATOR,
	FORMAL_PARAMETER_LIST,
	FORMAL_PARAMETER,
	// THROWS_CLAUSE,
	CLASS_TYPE_LIST,
	METHOD_BODY,
	// STATIC_INITIALIZER,
	CONSTRUCTOR_DECLARATION,
	CONSTRUCTOR_DECLARATOR,
	// EXPLICIT_CONSTRUCTOR_INVOCATION_OPT,
	BLOCK_STATEMENTS_OPT,
	CONSTRUCTOR_BODY,
	ARGUMENT_LIST_OPT,
	// EXPLICIT_CONSTRUCTOR_INVOCATION,
	EXTENDS_INTERFACES_OPT,
	INTERFACE_DECLARATION,
	EXTENDS_INTERFACES,
	INTERFACE_MEMBER_DECLARATIONS_OPT,
	INTERFACE_BODY,
	INTERFACE_MEMBER_DECLARATIONS,
	INTERFACE_MEMBER_DECLARATION,
	CONSTANT_DECLARATION,
	ABSTRACT_METHOD_DECLARATION,
	VARIABLE_INITIALIZERS_OPT,
	COMMA_OPT,
	// ARRAY_INITIALIZER,
	VARIABLE_INITIALIZERS,
	BLOCK,
	BLOCK_STATEMENTS,
	BLOCK_STATEMENT,
	LOCAL_VARIABLE_DECLARATION_STATEMENT,
	LOCAL_VARIABLE_DECLARATION,
	STATEMENT,
	STATEMENT_NO_SHORT_IF,
	STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT,
	EMPTY_STATEMENT,
	LABELED_STATEMENT,
	LABELED_STATEMENT_NO_SHORT_IF,
	EXPRESSION_STATEMENT,
	STATEMENT_EXPRESSION,
	IF_THEN_STATEMENT,
	IF_THEN_ELSE_STATEMENT,
	IF_THEN_ELSE_STATEMENT_NO_SHORT_IF,
	// SWITCH_STATEMENT,
	// SWITCH_BLOCK_STATEMENT_GROUPS_OPT,
	// SWITCH_LABELS_OPT,
	// SWITCH_BLOCK,
	// SWITCH_BLOCK_STATEMENT_GROUPS,
	// SWITCH_BLOCK_STATEMENT_GROUP,
	// SWITCH_LABELS,
	// SWITCH_LABEL,
	WHILE_STATEMENT,
	WHILE_STATEMENT_NO_SHORT_IF,
	// DO_STATEMENT,
	FOR_INIT_OPT,
	EXPRESSION_OPT,
	FOR_UPDATE_OPT,
	FOR_STATEMENT,
	FOR_STATEMENT_NO_SHORT_IF,
	FOR_INIT,
	FOR_UPDATE,
	STATEMENT_EXPRESSION_LIST,
	IDENTIFIER_OPT,
	// BREAK_STATEMENT,
	// CONTINUE_STATEMENT,
	RETURN_STATEMENT,
	// THROW_STATEMENT,
	// SYNCHRONIZED_STATEMENT,
	// CATCHES_OPT,
	// TRY_STATEMENT,
	// CATCHES,
	// CATCH_CLAUSE,
	// FINALLY_CLAUSE,
	PRIMARY,
	PRIMARY_NO_NEW_ARRAY,
	CLASS_INSTANCE_CREATION_EXPRESSION,
	ARGUMENT_LIST,
	DIMS_OPT,
	ARRAY_CREATION_EXPRESSION,
	DIM_EXPRS,
	DIM_EXPR,
	DIMS,
	FIELD_ACCESS,
	METHOD_INVOCATION,
	ARRAY_ACCESS,
	POSTFIX_EXPRESSION,
	// POST_INCREMENT_EXPRESSION,
	// POST_DECREMENT_EXPRESSION,
	UNARY_EXPRESSION,
	// PRE_INCREMENT_EXPRESSION,
	// PRE_DECREMENT_EXPRESSION,
	UNARY_EXPRESSION_NOT_PLUS_MINUS,
	CAST_EXPRESSION,
	MULTIPLICATIVE_EXPRESSION,
	ADDITIVE_EXPRESSION,
	SHIFT_EXPRESSION,
	RELATIONAL_EXPRESSION,
	EQUALITY_EXPRESSION,
	AND_EXPRESSION,
	EXCLUSIVE_OR_EXPRESSION,
	INCLUSIVE_OR_EXPRESSION,
	CONDITIONAL_AND_EXPRESSION,
	CONDITIONAL_OR_EXPRESSION,
	CONDITIONAL_EXPRESSION,
	ASSIGNMENT_EXPRESSION,
	ASSIGNMENT,
	LEFT_HAND_SIDE,
	ASSIGNMENT_OPERATOR,
	EXPRESSION,
	CONSTANT_EXPRESSION
}
