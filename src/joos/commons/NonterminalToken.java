package joos.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import joos.commons.Token;
import joos.commons.TokenType;

public class NonterminalToken implements Token {
  public final TokenType mType;

  public static Map<TokenType, NonterminalToken> mTokens = null;

  private NonterminalToken(TokenType type) {
    this.mType = type;
  }

  public TokenType getType() {
    return this.mType;
  }

	public static Set<TokenType> getAllTokens() {
		if (mTokens == null) {
			generateTokens();
		}
		return mTokens.keySet();
	}

  public static Token getToken(TokenType tokenType) {
    if (mTokens == null) {
      generateTokens();
    }
    return mTokens.get(tokenType);
  }

  private static void generateTokens() {
    mTokens = new HashMap();
    mTokens.put(TokenType.GOAL, new NonterminalToken(TokenType.GOAL));
    mTokens.put(TokenType.LITERAL, new NonterminalToken(TokenType.LITERAL));
    mTokens.put(TokenType.BOOLEAN_LITERAL, new NonterminalToken(TokenType.BOOLEAN_LITERAL));
    mTokens.put(TokenType.TYPE, new NonterminalToken(TokenType.TYPE));
    mTokens.put(TokenType.PRIMITIVE_TYPE, new NonterminalToken(TokenType.PRIMITIVE_TYPE));
    mTokens.put(TokenType.NUMERIC_TYPE, new NonterminalToken(TokenType.NUMERIC_TYPE));
    mTokens.put(TokenType.INTEGRAL_TYPE, new NonterminalToken(TokenType.INTEGRAL_TYPE));
    mTokens.put(TokenType.FLOATING_POINT_TYPE, new NonterminalToken(TokenType.FLOATING_POINT_TYPE));
    mTokens.put(TokenType.REFERENCE_TYPE, new NonterminalToken(TokenType.REFERENCE_TYPE));
    mTokens.put(TokenType.CLASS_OR_INTERFACE_TYPE, new NonterminalToken(TokenType.CLASS_OR_INTERFACE_TYPE));
    mTokens.put(TokenType.CLASS_TYPE, new NonterminalToken(TokenType.CLASS_TYPE));
    mTokens.put(TokenType.INTERFACE_TYPE, new NonterminalToken(TokenType.INTERFACE_TYPE));
    mTokens.put(TokenType.ARRAY_TYPE, new NonterminalToken(TokenType.ARRAY_TYPE));
    mTokens.put(TokenType.NAME, new NonterminalToken(TokenType.NAME));
    mTokens.put(TokenType.SIMPLE_NAME, new NonterminalToken(TokenType.SIMPLE_NAME));
    mTokens.put(TokenType.QUALIFIED_NAME, new NonterminalToken(TokenType.QUALIFIED_NAME));
    mTokens.put(TokenType.PACKAGE_DECLARATION_OPT, new NonterminalToken(TokenType.PACKAGE_DECLARATION_OPT));
    mTokens.put(TokenType.IMPORT_DECLARATIONS_OPT, new NonterminalToken(TokenType.IMPORT_DECLARATIONS_OPT));
    mTokens.put(TokenType.TYPE_DECLARATIONS_OPT, new NonterminalToken(TokenType.TYPE_DECLARATIONS_OPT));
    mTokens.put(TokenType.COMPILATION_UNIT, new NonterminalToken(TokenType.COMPILATION_UNIT));
    mTokens.put(TokenType.IMPORT_DECLARATIONS, new NonterminalToken(TokenType.IMPORT_DECLARATIONS));
    mTokens.put(TokenType.TYPE_DECLARATIONS, new NonterminalToken(TokenType.TYPE_DECLARATIONS));
    mTokens.put(TokenType.PACKAGE_DECLARATION, new NonterminalToken(TokenType.PACKAGE_DECLARATION));
    mTokens.put(TokenType.IMPORT_DECLARATION, new NonterminalToken(TokenType.IMPORT_DECLARATION));
    mTokens.put(TokenType.SINGLE_TYPE_IMPORT_DECLARATION, new NonterminalToken(TokenType.SINGLE_TYPE_IMPORT_DECLARATION));
    mTokens.put(TokenType.TYPE_IMPORT_ON_DEMAND_DECLARATION, new NonterminalToken(TokenType.TYPE_IMPORT_ON_DEMAND_DECLARATION));
    mTokens.put(TokenType.TYPE_DECLARATION, new NonterminalToken(TokenType.TYPE_DECLARATION));
    mTokens.put(TokenType.MODIFIERS, new NonterminalToken(TokenType.MODIFIERS));
    mTokens.put(TokenType.MODIFIER, new NonterminalToken(TokenType.MODIFIER));
    mTokens.put(TokenType.MODIFIERS_OPT, new NonterminalToken(TokenType.MODIFIERS_OPT));
    mTokens.put(TokenType.SUPER_CLAUSE_OPT, new NonterminalToken(TokenType.SUPER_CLAUSE_OPT));
    mTokens.put(TokenType.INTERFACES_OPT, new NonterminalToken(TokenType.INTERFACES_OPT));
    mTokens.put(TokenType.CLASS_DECLARATION, new NonterminalToken(TokenType.CLASS_DECLARATION));
    mTokens.put(TokenType.SUPER_CLAUSE, new NonterminalToken(TokenType.SUPER_CLAUSE));
    mTokens.put(TokenType.INTERFACES, new NonterminalToken(TokenType.INTERFACES));
    mTokens.put(TokenType.INTERFACE_TYPE_LIST, new NonterminalToken(TokenType.INTERFACE_TYPE_LIST));
    mTokens.put(TokenType.CLASS_BODY_DECLARATIONS_OPT, new NonterminalToken(TokenType.CLASS_BODY_DECLARATIONS_OPT));
    mTokens.put(TokenType.CLASS_BODY, new NonterminalToken(TokenType.CLASS_BODY));
    mTokens.put(TokenType.CLASS_BODY_DECLARATIONS, new NonterminalToken(TokenType.CLASS_BODY_DECLARATIONS));
    mTokens.put(TokenType.CLASS_BODY_DECLARATION, new NonterminalToken(TokenType.CLASS_BODY_DECLARATION));
    mTokens.put(TokenType.CLASS_MEMBER_DECLARATION, new NonterminalToken(TokenType.CLASS_MEMBER_DECLARATION));
    mTokens.put(TokenType.FIELD_DECLARATION, new NonterminalToken(TokenType.FIELD_DECLARATION));
    mTokens.put(TokenType.VARIABLE_DECLARATORS, new NonterminalToken(TokenType.VARIABLE_DECLARATORS));
    mTokens.put(TokenType.VARIABLE_DECLARATOR, new NonterminalToken(TokenType.VARIABLE_DECLARATOR));
    mTokens.put(TokenType.VARIABLE_DECLARATOR_ID, new NonterminalToken(TokenType.VARIABLE_DECLARATOR_ID));
    mTokens.put(TokenType.VARIABLE_INITIALIZER, new NonterminalToken(TokenType.VARIABLE_INITIALIZER));
    mTokens.put(TokenType.METHOD_DECLARATION, new NonterminalToken(TokenType.METHOD_DECLARATION));
    mTokens.put(TokenType.THROWS_CLAUSE_OPT, new NonterminalToken(TokenType.THROWS_CLAUSE_OPT));
    mTokens.put(TokenType.METHOD_HEADER, new NonterminalToken(TokenType.METHOD_HEADER));
    mTokens.put(TokenType.FORMAL_PARAMETER_LIST_OPT, new NonterminalToken(TokenType.FORMAL_PARAMETER_LIST_OPT));
    mTokens.put(TokenType.METHOD_DECLARATOR, new NonterminalToken(TokenType.METHOD_DECLARATOR));
    mTokens.put(TokenType.FORMAL_PARAMETER_LIST, new NonterminalToken(TokenType.FORMAL_PARAMETER_LIST));
    mTokens.put(TokenType.FORMAL_PARAMETER, new NonterminalToken(TokenType.FORMAL_PARAMETER));
    mTokens.put(TokenType.THROWS_CLAUSE, new NonterminalToken(TokenType.THROWS_CLAUSE));
    mTokens.put(TokenType.CLASS_TYPE_LIST, new NonterminalToken(TokenType.CLASS_TYPE_LIST));
    mTokens.put(TokenType.METHOD_BODY, new NonterminalToken(TokenType.METHOD_BODY));
    mTokens.put(TokenType.STATIC_INITIALIZER, new NonterminalToken(TokenType.STATIC_INITIALIZER));
    mTokens.put(TokenType.CONSTRUCTOR_DECLARATION, new NonterminalToken(TokenType.CONSTRUCTOR_DECLARATION));
    mTokens.put(TokenType.CONSTRUCTOR_DECLARATOR, new NonterminalToken(TokenType.CONSTRUCTOR_DECLARATOR));
    mTokens.put(TokenType.EXPLICIT_CONSTRUCTOR_INVOCATION_OPT, new NonterminalToken(TokenType.EXPLICIT_CONSTRUCTOR_INVOCATION_OPT));
    mTokens.put(TokenType.BLOCK_STATEMENTS_OPT, new NonterminalToken(TokenType.BLOCK_STATEMENTS_OPT));
    mTokens.put(TokenType.CONSTRUCTOR_BODY, new NonterminalToken(TokenType.CONSTRUCTOR_BODY));
    mTokens.put(TokenType.ARGUMENT_LIST_OPT, new NonterminalToken(TokenType.ARGUMENT_LIST_OPT));
    mTokens.put(TokenType.EXPLICIT_CONSTRUCTOR_INVOCATION, new NonterminalToken(TokenType.EXPLICIT_CONSTRUCTOR_INVOCATION));
    mTokens.put(TokenType.EXTENDS_INTERFACES_OPT, new NonterminalToken(TokenType.EXTENDS_INTERFACES_OPT));
    mTokens.put(TokenType.INTERFACE_DECLARATION, new NonterminalToken(TokenType.INTERFACE_DECLARATION));
    mTokens.put(TokenType.EXTENDS_INTERFACES, new NonterminalToken(TokenType.EXTENDS_INTERFACES));
    mTokens.put(TokenType.INTERFACE_MEMBER_DECLARATIONS_OPT, new NonterminalToken(TokenType.INTERFACE_MEMBER_DECLARATIONS_OPT));
    mTokens.put(TokenType.INTERFACE_BODY, new NonterminalToken(TokenType.INTERFACE_BODY));
    mTokens.put(TokenType.INTERFACE_MEMBER_DECLARATIONS, new NonterminalToken(TokenType.INTERFACE_MEMBER_DECLARATIONS));
    mTokens.put(TokenType.INTERFACE_MEMBER_DECLARATION, new NonterminalToken(TokenType.INTERFACE_MEMBER_DECLARATION));
    mTokens.put(TokenType.CONSTANT_DECLARATION, new NonterminalToken(TokenType.CONSTANT_DECLARATION));
    mTokens.put(TokenType.ABSTRACT_METHOD_DECLARATION, new NonterminalToken(TokenType.ABSTRACT_METHOD_DECLARATION));
    mTokens.put(TokenType.VARIABLE_INITIALIZERS_OPT, new NonterminalToken(TokenType.VARIABLE_INITIALIZERS_OPT));
    mTokens.put(TokenType.COMMA_OPT, new NonterminalToken(TokenType.COMMA_OPT));
    mTokens.put(TokenType.ARRAY_INITIALIZER, new NonterminalToken(TokenType.ARRAY_INITIALIZER));
    mTokens.put(TokenType.VARIABLE_INITIALIZERS, new NonterminalToken(TokenType.VARIABLE_INITIALIZERS));
    mTokens.put(TokenType.BLOCK, new NonterminalToken(TokenType.BLOCK));
    mTokens.put(TokenType.BLOCK_STATEMENTS, new NonterminalToken(TokenType.BLOCK_STATEMENTS));
    mTokens.put(TokenType.BLOCK_STATEMENT, new NonterminalToken(TokenType.BLOCK_STATEMENT));
    mTokens.put(TokenType.LOCAL_VARIABLE_DECLARATION_STATEMENT, new NonterminalToken(TokenType.LOCAL_VARIABLE_DECLARATION_STATEMENT));
    mTokens.put(TokenType.LOCAL_VARIABLE_DECLARATION, new NonterminalToken(TokenType.LOCAL_VARIABLE_DECLARATION));
    mTokens.put(TokenType.STATEMENT, new NonterminalToken(TokenType.STATEMENT));
    mTokens.put(TokenType.STATEMENT_NO_SHORT_IF, new NonterminalToken(TokenType.STATEMENT_NO_SHORT_IF));
    mTokens.put(TokenType.STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT, new NonterminalToken(TokenType.STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT));
    mTokens.put(TokenType.EMPTY_STATEMENT, new NonterminalToken(TokenType.EMPTY_STATEMENT));
    mTokens.put(TokenType.LABELED_STATEMENT, new NonterminalToken(TokenType.LABELED_STATEMENT));
    mTokens.put(TokenType.LABELED_STATEMENT_NO_SHORT_IF, new NonterminalToken(TokenType.LABELED_STATEMENT_NO_SHORT_IF));
    mTokens.put(TokenType.EXPRESSION_STATEMENT, new NonterminalToken(TokenType.EXPRESSION_STATEMENT));
    mTokens.put(TokenType.STATEMENT_EXPRESSION, new NonterminalToken(TokenType.STATEMENT_EXPRESSION));
    mTokens.put(TokenType.IF_THEN_STATEMENT, new NonterminalToken(TokenType.IF_THEN_STATEMENT));
    mTokens.put(TokenType.IF_THEN_ELSE_STATEMENT, new NonterminalToken(TokenType.IF_THEN_ELSE_STATEMENT));
    mTokens.put(TokenType.IF_THEN_ELSE_STATEMENT_NO_SHORT_IF, new NonterminalToken(TokenType.IF_THEN_ELSE_STATEMENT_NO_SHORT_IF));
    mTokens.put(TokenType.SWITCH_STATEMENT, new NonterminalToken(TokenType.SWITCH_STATEMENT));
    mTokens.put(TokenType.SWITCH_BLOCK_STATEMENT_GROUPS_OPT, new NonterminalToken(TokenType.SWITCH_BLOCK_STATEMENT_GROUPS_OPT));
    mTokens.put(TokenType.SWITCH_LABELS_OPT, new NonterminalToken(TokenType.SWITCH_LABELS_OPT));
    mTokens.put(TokenType.SWITCH_BLOCK, new NonterminalToken(TokenType.SWITCH_BLOCK));
    mTokens.put(TokenType.SWITCH_BLOCK_STATEMENT_GROUPS, new NonterminalToken(TokenType.SWITCH_BLOCK_STATEMENT_GROUPS));
    mTokens.put(TokenType.SWITCH_BLOCK_STATEMENT_GROUP, new NonterminalToken(TokenType.SWITCH_BLOCK_STATEMENT_GROUP));
    mTokens.put(TokenType.SWITCH_LABELS, new NonterminalToken(TokenType.SWITCH_LABELS));
    mTokens.put(TokenType.SWITCH_LABEL, new NonterminalToken(TokenType.SWITCH_LABEL));
    mTokens.put(TokenType.WHILE_STATEMENT, new NonterminalToken(TokenType.WHILE_STATEMENT));
    mTokens.put(TokenType.WHILE_STATEMENT_NO_SHORT_IF, new NonterminalToken(TokenType.WHILE_STATEMENT_NO_SHORT_IF));
    mTokens.put(TokenType.DO_STATEMENT, new NonterminalToken(TokenType.DO_STATEMENT));
    mTokens.put(TokenType.FOR_INIT_OPT, new NonterminalToken(TokenType.FOR_INIT_OPT));
    mTokens.put(TokenType.EXPRESSION_OPT, new NonterminalToken(TokenType.EXPRESSION_OPT));
    mTokens.put(TokenType.FOR_UPDATE_OPT, new NonterminalToken(TokenType.FOR_UPDATE_OPT));
    mTokens.put(TokenType.FOR_STATEMENT, new NonterminalToken(TokenType.FOR_STATEMENT));
    mTokens.put(TokenType.FOR_STATEMENT_NO_SHORT_IF, new NonterminalToken(TokenType.FOR_STATEMENT_NO_SHORT_IF));
    mTokens.put(TokenType.FOR_INIT, new NonterminalToken(TokenType.FOR_INIT));
    mTokens.put(TokenType.FOR_UPDATE, new NonterminalToken(TokenType.FOR_UPDATE));
    mTokens.put(TokenType.STATEMENT_EXPRESSION_LIST, new NonterminalToken(TokenType.STATEMENT_EXPRESSION_LIST));
    mTokens.put(TokenType.IDENTIFIER_OPT, new NonterminalToken(TokenType.IDENTIFIER_OPT));
    mTokens.put(TokenType.BREAK_STATEMENT, new NonterminalToken(TokenType.BREAK_STATEMENT));
    mTokens.put(TokenType.CONTINUE_STATEMENT, new NonterminalToken(TokenType.CONTINUE_STATEMENT));
    mTokens.put(TokenType.RETURN_STATEMENT, new NonterminalToken(TokenType.RETURN_STATEMENT));
    mTokens.put(TokenType.THROW_STATEMENT, new NonterminalToken(TokenType.THROW_STATEMENT));
    mTokens.put(TokenType.SYNCHRONIZED_STATEMENT, new NonterminalToken(TokenType.SYNCHRONIZED_STATEMENT));
    mTokens.put(TokenType.CATCHES_OPT, new NonterminalToken(TokenType.CATCHES_OPT));
    mTokens.put(TokenType.TRY_STATEMENT, new NonterminalToken(TokenType.TRY_STATEMENT));
    mTokens.put(TokenType.CATCHES, new NonterminalToken(TokenType.CATCHES));
    mTokens.put(TokenType.CATCH_CLAUSE, new NonterminalToken(TokenType.CATCH_CLAUSE));
    mTokens.put(TokenType.FINALLY_CLAUSE, new NonterminalToken(TokenType.FINALLY_CLAUSE));
    mTokens.put(TokenType.PRIMARY, new NonterminalToken(TokenType.PRIMARY));
    mTokens.put(TokenType.PRIMARY_NO_NEW_ARRAY, new NonterminalToken(TokenType.PRIMARY_NO_NEW_ARRAY));
    mTokens.put(TokenType.CLASS_INSTANCE_CREATION_EXPRESSION, new NonterminalToken(TokenType.CLASS_INSTANCE_CREATION_EXPRESSION));
    mTokens.put(TokenType.ARGUMENT_LIST, new NonterminalToken(TokenType.ARGUMENT_LIST));
    mTokens.put(TokenType.DIMS_OPT, new NonterminalToken(TokenType.DIMS_OPT));
    mTokens.put(TokenType.ARRAY_CREATION_EXPRESSION, new NonterminalToken(TokenType.ARRAY_CREATION_EXPRESSION));
    mTokens.put(TokenType.DIM_EXPRS, new NonterminalToken(TokenType.DIM_EXPRS));
    mTokens.put(TokenType.DIM_EXPR, new NonterminalToken(TokenType.DIM_EXPR));
    mTokens.put(TokenType.DIMS, new NonterminalToken(TokenType.DIMS));
    mTokens.put(TokenType.FIELD_ACCESS, new NonterminalToken(TokenType.FIELD_ACCESS));
    mTokens.put(TokenType.METHOD_INVOCATION, new NonterminalToken(TokenType.METHOD_INVOCATION));
    mTokens.put(TokenType.ARRAY_ACCESS, new NonterminalToken(TokenType.ARRAY_ACCESS));
    mTokens.put(TokenType.POSTFIX_EXPRESSION, new NonterminalToken(TokenType.POSTFIX_EXPRESSION));
    mTokens.put(TokenType.POST_INCREMENT_EXPRESSION, new NonterminalToken(TokenType.POST_INCREMENT_EXPRESSION));
    mTokens.put(TokenType.POST_DECREMENT_EXPRESSION, new NonterminalToken(TokenType.POST_DECREMENT_EXPRESSION));
    mTokens.put(TokenType.UNARY_EXPRESSION, new NonterminalToken(TokenType.UNARY_EXPRESSION));
    mTokens.put(TokenType.PRE_INCREMENT_EXPRESSION, new NonterminalToken(TokenType.PRE_INCREMENT_EXPRESSION));
    mTokens.put(TokenType.PRE_DECREMENT_EXPRESSION, new NonterminalToken(TokenType.PRE_DECREMENT_EXPRESSION));
    mTokens.put(TokenType.UNARY_EXPRESSION_NOT_PLUS_MINUS, new NonterminalToken(TokenType.UNARY_EXPRESSION_NOT_PLUS_MINUS));
    mTokens.put(TokenType.CAST_EXPRESSION, new NonterminalToken(TokenType.CAST_EXPRESSION));
    mTokens.put(TokenType.MULTIPLICATIVE_EXPRESSION, new NonterminalToken(TokenType.MULTIPLICATIVE_EXPRESSION));
    mTokens.put(TokenType.ADDITIVE_EXPRESSION, new NonterminalToken(TokenType.ADDITIVE_EXPRESSION));
    mTokens.put(TokenType.SHIFT_EXPRESSION, new NonterminalToken(TokenType.SHIFT_EXPRESSION));
    mTokens.put(TokenType.RELATIONAL_EXPRESSION, new NonterminalToken(TokenType.RELATIONAL_EXPRESSION));
    mTokens.put(TokenType.EQUALITY_EXPRESSION, new NonterminalToken(TokenType.EQUALITY_EXPRESSION));
    mTokens.put(TokenType.AND_EXPRESSION, new NonterminalToken(TokenType.AND_EXPRESSION));
    mTokens.put(TokenType.EXCLUSIVE_OR_EXPRESSION, new NonterminalToken(TokenType.EXCLUSIVE_OR_EXPRESSION));
    mTokens.put(TokenType.INCLUSIVE_OR_EXPRESSION, new NonterminalToken(TokenType.INCLUSIVE_OR_EXPRESSION));
    mTokens.put(TokenType.CONDITIONAL_AND_EXPRESSION, new NonterminalToken(TokenType.CONDITIONAL_AND_EXPRESSION));
    mTokens.put(TokenType.CONDITIONAL_OR_EXPRESSION, new NonterminalToken(TokenType.CONDITIONAL_OR_EXPRESSION));
    mTokens.put(TokenType.CONDITIONAL_EXPRESSION, new NonterminalToken(TokenType.CONDITIONAL_EXPRESSION));
    mTokens.put(TokenType.ASSIGNMENT_EXPRESSION, new NonterminalToken(TokenType.ASSIGNMENT_EXPRESSION));
    mTokens.put(TokenType.ASSIGNMENT, new NonterminalToken(TokenType.ASSIGNMENT));
    mTokens.put(TokenType.LEFT_HAND_SIDE, new NonterminalToken(TokenType.LEFT_HAND_SIDE));
    mTokens.put(TokenType.ASSIGNMENT_OPERATOR, new NonterminalToken(TokenType.ASSIGNMENT_OPERATOR));
    mTokens.put(TokenType.EXPRESSION, new NonterminalToken(TokenType.EXPRESSION));
    mTokens.put(TokenType.CONSTANT_EXPRESSION, new NonterminalToken(TokenType.CONSTANT_EXPRESSION));
  }
}
