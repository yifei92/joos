package joos.parser;

import joos.commons.TokenType;
import joos.commons.NonterminalToken;
import joos.commons.TerminalToken;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import joos.parser.Grammar;


public final class JavaGrammar {
  public static final Grammar grammar;
  private JavaGrammar() { }
  static {
    HashMap<TokenType, List<List<TokenType>>> productions = new HashMap();
    productions.put(
      TokenType.GOAL,
      Arrays.asList(
        Arrays.asList(TokenType.COMPILATION_UNIT)
      )
    );
    productions.put(
      TokenType.BOOLEAN_LITERAL,
      Arrays.asList(
        Arrays.asList(TokenType.BOOLEAN_LITERAL_TRUE),
        Arrays.asList(TokenType.BOOLEAN_LITERAL_FALSE)
      )
    );
    productions.put(
      TokenType.LITERAL,
      Arrays.asList(
        Arrays.asList(TokenType.INTEGER_LITERAL),
        Arrays.asList(TokenType.FLOATING_POINT_LITERAL),
        Arrays.asList(TokenType.BOOLEAN_LITERAL),
        Arrays.asList(TokenType.CHAR_LITERAL_WITH_QUOTES),
        Arrays.asList(TokenType.STRING_LITERAL_WITH_QUOTES),
        Arrays.asList(TokenType.NULL_LITERAL)
      )
    );
    productions.put(
      TokenType.STRING_LITERAL_WITH_QUOTES,
      Arrays.asList(
        Arrays.asList(TokenType.DOUBLE_QUOTE, TokenType.STRING_LITERAL_WITH_ESCAPES, TokenType.DOUBLE_QUOTE)
      )
    );
    productions.put(
      TokenType.STRING_LITERAL_WITH_ESCAPES,
      Arrays.asList(
        Arrays.asList(TokenType.CHARACTER_ESCAPE),
        Arrays.asList(TokenType.STRING_LITERAL),
        Arrays.asList(TokenType.STRING_LITERAL_WITH_ESCAPES, TokenType.STRING_LITERAL),
        Arrays.asList(TokenType.STRING_LITERAL_WITH_ESCAPES, TokenType.CHARACTER_ESCAPE)
      )
    );
    productions.put(
      TokenType.CHAR_LITERAL_WITH_QUOTES,
      Arrays.asList(
        Arrays.asList(TokenType.SINGLE_QUOTE, TokenType.CHAR_LITERAL, TokenType.SINGLE_QUOTE),
        Arrays.asList(TokenType.SINGLE_QUOTE, TokenType.CHARACTER_ESCAPE, TokenType.CHAR_LITERAL, TokenType.SINGLE_QUOTE)
      )
    );
    productions.put(
      TokenType.TYPE,
      Arrays.asList(
        Arrays.asList(TokenType.PRIMITIVE_TYPE),
        Arrays.asList(TokenType.REFERENCE_TYPE)
      )
    );
    productions.put(
      TokenType.PRIMITIVE_TYPE,
      Arrays.asList(
        Arrays.asList(TokenType.NUMERIC_TYPE),
        Arrays.asList(TokenType.BOOLEAN),
        Arrays.asList()
      )
    );
    productions.put(
      TokenType.NUMERIC_TYPE,
      Arrays.asList(
        Arrays.asList(TokenType.INTEGRAL_TYPE),
        Arrays.asList(TokenType.FLOATING_POINT_TYPE)
      )
    );
    productions.put(
      TokenType.INTEGRAL_TYPE,
      Arrays.asList(
        Arrays.asList(TokenType.BYTE),
        Arrays.asList(TokenType.SHORT),
        Arrays.asList(TokenType.INT),
        Arrays.asList(TokenType.LONG),
        Arrays.asList(TokenType.CHAR)
      )
    );
    productions.put(
      TokenType.FLOATING_POINT_TYPE,
      Arrays.asList(
        Arrays.asList(TokenType.FLOAT),
        Arrays.asList(TokenType.DOUBLE)
      )
    );
    productions.put(
      TokenType.REFERENCE_TYPE,
      Arrays.asList(
        Arrays.asList(TokenType.CLASS_OR_INTERFACE_TYPE),
        Arrays.asList(TokenType.ARRAY_TYPE)
      )
    );
    productions.put(
      TokenType.CLASS_OR_INTERFACE_TYPE,
      Arrays.asList(
        Arrays.asList(TokenType.NAME)
      )
    );
    productions.put(
      TokenType.CLASS_TYPE,
      Arrays.asList(
        Arrays.asList(TokenType.CLASS_OR_INTERFACE_TYPE)
      )
    );
    productions.put(
      TokenType.INTERFACE_TYPE,
      Arrays.asList(
        Arrays.asList(TokenType.CLASS_OR_INTERFACE_TYPE)
      )
    );
    productions.put(
      TokenType.ARRAY_TYPE,
      Arrays.asList(
        Arrays.asList(TokenType.PRIMITIVE_TYPE, TokenType.OPEN_SBR, TokenType.CLOSE_SBR),
        Arrays.asList(TokenType.NAME, TokenType.OPEN_SBR, TokenType.CLOSE_SBR),
        Arrays.asList(TokenType.ARRAY_TYPE, TokenType.OPEN_SBR, TokenType.CLOSE_SBR)
      )
    );
    productions.put(
      TokenType.NAME,
      Arrays.asList(
        Arrays.asList(TokenType.SIMPLE_NAME),
        Arrays.asList(TokenType.QUALIFIED_NAME)
      )
    );
    productions.put(
      TokenType.SIMPLE_NAME,
      Arrays.asList(
        Arrays.asList(TokenType.IDENTIFIER)
      )
    );
    productions.put(
      TokenType.QUALIFIED_NAME,
      Arrays.asList(
        Arrays.asList(TokenType.NAME, TokenType.DOT, TokenType.IDENTIFIER)
      )
    );
    productions.put(
      TokenType.PACKAGE_DECLARATION_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.PACKAGE_DECLARATION)
      )
    );
    productions.put(
      TokenType.IMPORT_DECLARATIONS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.IMPORT_DECLARATIONS)
      )
    );
    productions.put(
      TokenType.TYPE_DECLARATIONS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.TYPE_DECLARATIONS)
      )
    );
    productions.put(
      TokenType.COMPILATION_UNIT,
      Arrays.asList(
        Arrays.asList(TokenType.PACKAGE_DECLARATION_OPT, TokenType.IMPORT_DECLARATIONS_OPT, TokenType.TYPE_DECLARATIONS_OPT)
      )
    );
    productions.put(
      TokenType.IMPORT_DECLARATIONS,
      Arrays.asList(
        Arrays.asList(TokenType.IMPORT_DECLARATION),
        Arrays.asList(TokenType.IMPORT_DECLARATIONS, TokenType.IMPORT_DECLARATION)
      )
    );
    productions.put(
      TokenType.TYPE_DECLARATIONS,
      Arrays.asList(
        Arrays.asList(TokenType.TYPE_DECLARATION),
        Arrays.asList(TokenType.TYPE_DECLARATIONS, TokenType.TYPE_DECLARATION)
      )
    );
    productions.put(
      TokenType.PACKAGE_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.PACKAGE, TokenType.NAME, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.IMPORT_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.SINGLE_TYPE_IMPORT_DECLARATION),
        Arrays.asList(TokenType.TYPE_IMPORT_ON_DEMAND_DECLARATION)
      )
    );
    productions.put(
      TokenType.SINGLE_TYPE_IMPORT_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.IMPORT, TokenType.NAME, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.TYPE_IMPORT_ON_DEMAND_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.IMPORT, TokenType.NAME, TokenType.DOT, TokenType.STAR, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.TYPE_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.CLASS_DECLARATION),
        Arrays.asList(TokenType.INTERFACE_DECLARATION),
        Arrays.asList(TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.MODIFIERS,
      Arrays.asList(
        Arrays.asList(TokenType.MODIFIER),
        Arrays.asList(TokenType.MODIFIERS, TokenType.MODIFIER)
      )
    );
    productions.put(
      TokenType.MODIFIER,
      Arrays.asList(
        Arrays.asList(TokenType.PUBLIC),
        Arrays.asList(TokenType.PROTECTED),
        Arrays.asList(TokenType.PRIVATE),
        Arrays.asList(TokenType.STATIC),
        Arrays.asList(TokenType.ABSTRACT),
        Arrays.asList(TokenType.FINAL),
        Arrays.asList(TokenType.NATIVE),
        Arrays.asList(TokenType.SYNCHRONIZED),
        Arrays.asList(TokenType.TRANSIENT),
        Arrays.asList(TokenType.VOLATILE)
      )
    );
    productions.put(
      TokenType.MODIFIERS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.MODIFIERS)
      )
    );
    productions.put(
      TokenType.SUPER_CLAUSE_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.SUPER_CLAUSE)
      )
    );
    productions.put(
      TokenType.INTERFACES_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.INTERFACES)
      )
    );
    productions.put(
      TokenType.CLASS_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.MODIFIERS_OPT, TokenType.CLASS, TokenType.IDENTIFIER, TokenType.SUPER_CLAUSE_OPT, TokenType.INTERFACES_OPT, TokenType.CLASS_BODY)
      )
    );
    productions.put(
      TokenType.SUPER_CLAUSE,
      Arrays.asList(
        Arrays.asList(TokenType.EXTENDS, TokenType.CLASS_TYPE)
      )
    );
    productions.put(
      TokenType.INTERFACES,
      Arrays.asList(
        Arrays.asList(TokenType.IMPLEMENTS, TokenType.INTERFACE_TYPE_LIST)
      )
    );
    productions.put(
      TokenType.INTERFACE_TYPE_LIST,
      Arrays.asList(
        Arrays.asList(TokenType.INTERFACE_TYPE),
        Arrays.asList(TokenType.INTERFACE_TYPE_LIST, TokenType.COMMA, TokenType.INTERFACE_TYPE)
      )
    );
    productions.put(
      TokenType.CLASS_BODY_DECLARATIONS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.CLASS_BODY_DECLARATIONS)
      )
    );
    productions.put(
      TokenType.CLASS_BODY,
      Arrays.asList(
        Arrays.asList(TokenType.OPEN_CBR, TokenType.CLASS_BODY_DECLARATIONS_OPT, TokenType.CLOSE_CBR)
      )
    );
    productions.put(
      TokenType.CLASS_BODY_DECLARATIONS,
      Arrays.asList(
        Arrays.asList(TokenType.CLASS_BODY_DECLARATION),
        Arrays.asList(TokenType.CLASS_BODY_DECLARATIONS, TokenType.CLASS_BODY_DECLARATION)
      )
    );
    productions.put(
      TokenType.CLASS_BODY_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.CLASS_MEMBER_DECLARATION),
        Arrays.asList(TokenType.STATIC_INITIALIZER),
        Arrays.asList(TokenType.CONSTRUCTOR_DECLARATION)
      )
    );
    productions.put(
      TokenType.CLASS_MEMBER_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.FIELD_DECLARATION),
        Arrays.asList(TokenType.METHOD_DECLARATION)
      )
    );
    productions.put(
      TokenType.FIELD_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.MODIFIERS_OPT, TokenType.TYPE, TokenType.VARIABLE_DECLARATORS, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.VARIABLE_DECLARATORS,
      Arrays.asList(
        Arrays.asList(TokenType.VARIABLE_DECLARATOR),
        Arrays.asList(TokenType.VARIABLE_DECLARATORS, TokenType.COMMA, TokenType.VARIABLE_DECLARATOR)
      )
    );
    productions.put(
      TokenType.VARIABLE_DECLARATOR,
      Arrays.asList(
        Arrays.asList(TokenType.VARIABLE_DECLARATOR_ID),
        Arrays.asList(TokenType.VARIABLE_DECLARATOR_ID, TokenType.ASSIGN, TokenType.VARIABLE_INITIALIZER)
      )
    );
    productions.put(
      TokenType.VARIABLE_DECLARATOR_ID,
      Arrays.asList(
        Arrays.asList(TokenType.IDENTIFIER),
        Arrays.asList(TokenType.VARIABLE_DECLARATOR_ID, TokenType.OPEN_SBR, TokenType.CLOSE_SBR)
      )
    );
    productions.put(
      TokenType.VARIABLE_INITIALIZER,
      Arrays.asList(
        Arrays.asList(TokenType.EXPRESSION),
        Arrays.asList(TokenType.ARRAY_INITIALIZER)
      )
    );
    productions.put(
      TokenType.METHOD_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.METHOD_HEADER, TokenType.METHOD_BODY)
      )
    );
    productions.put(
      TokenType.THROWS_CLAUSE_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.THROWS_CLAUSE)
      )
    );
    productions.put(
      TokenType.METHOD_HEADER,
      Arrays.asList(
        Arrays.asList(TokenType.MODIFIERS_OPT, TokenType.TYPE, TokenType.METHOD_DECLARATOR, TokenType.THROWS_CLAUSE_OPT),
        Arrays.asList(TokenType.MODIFIERS_OPT, TokenType.VOID, TokenType.METHOD_DECLARATOR, TokenType.THROWS_CLAUSE_OPT)
      )
    );
    productions.put(
      TokenType.FORMAL_PARAMETER_LIST_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.FORMAL_PARAMETER_LIST)
      )
    );
    productions.put(
      TokenType.METHOD_DECLARATOR,
      Arrays.asList(
        Arrays.asList(TokenType.IDENTIFIER, TokenType.OPEN_BR, TokenType.FORMAL_PARAMETER_LIST_OPT, TokenType.CLOSE_BR),
        Arrays.asList(TokenType.METHOD_DECLARATOR, TokenType.OPEN_SBR, TokenType.CLOSE_SBR)
      )
    );
    productions.put(
      TokenType.FORMAL_PARAMETER_LIST,
      Arrays.asList(
        Arrays.asList(TokenType.FORMAL_PARAMETER),
        Arrays.asList(TokenType.FORMAL_PARAMETER_LIST, TokenType.COMMA, TokenType.FORMAL_PARAMETER)
      )
    );
    productions.put(
      TokenType.FORMAL_PARAMETER,
      Arrays.asList(
        Arrays.asList(TokenType.TYPE, TokenType.VARIABLE_DECLARATOR_ID)
      )
    );
    productions.put(
      TokenType.THROWS_CLAUSE,
      Arrays.asList(
        Arrays.asList(TokenType.THROWS, TokenType.CLASS_TYPE_LIST)
      )
    );
    productions.put(
      TokenType.CLASS_TYPE_LIST,
      Arrays.asList(
        Arrays.asList(TokenType.CLASS_TYPE),
        Arrays.asList(TokenType.CLASS_TYPE_LIST, TokenType.COMMA, TokenType.CLASS_TYPE)
      )
    );
    productions.put(
      TokenType.METHOD_BODY,
      Arrays.asList(
        Arrays.asList(TokenType.BLOCK),
        Arrays.asList(TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.STATIC_INITIALIZER,
      Arrays.asList(
        Arrays.asList(TokenType.STATIC, TokenType.BLOCK)
      )
    );
    productions.put(
      TokenType.CONSTRUCTOR_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.MODIFIERS_OPT, TokenType.CONSTRUCTOR_DECLARATOR, TokenType.THROWS_CLAUSE_OPT, TokenType.CONSTRUCTOR_BODY)
      )
    );
    productions.put(
      TokenType.CONSTRUCTOR_DECLARATOR,
      Arrays.asList(
        Arrays.asList(TokenType.SIMPLE_NAME, TokenType.OPEN_BR, TokenType.FORMAL_PARAMETER_LIST_OPT, TokenType.CLOSE_BR)
      )
    );
    productions.put(
      TokenType.EXPLICIT_CONSTRUCTOR_INVOCATION_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.EXPLICIT_CONSTRUCTOR_INVOCATION)
      )
    );
    productions.put(
      TokenType.BLOCK_STATEMENTS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.BLOCK_STATEMENTS)
      )
    );
    productions.put(
      TokenType.CONSTRUCTOR_BODY,
      Arrays.asList(
        Arrays.asList(TokenType.OPEN_CBR, TokenType.EXPLICIT_CONSTRUCTOR_INVOCATION_OPT, TokenType.BLOCK_STATEMENTS_OPT, TokenType.CLOSE_CBR)
      )
    );
    productions.put(
      TokenType.ARGUMENT_LIST_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.ARGUMENT_LIST)
      )
    );
    productions.put(
      TokenType.EXPLICIT_CONSTRUCTOR_INVOCATION,
      Arrays.asList(
        Arrays.asList(TokenType.THIS, TokenType.OPEN_BR, TokenType.ARGUMENT_LIST_OPT, TokenType.CLOSE_BR, TokenType.SEMICOLON),
        Arrays.asList(TokenType.SUPER, TokenType.OPEN_BR, TokenType.ARGUMENT_LIST_OPT, TokenType.CLOSE_BR, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.EXTENDS_INTERFACES_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.EXTENDS_INTERFACES)
      )
    );
    productions.put(
      TokenType.INTERFACE_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.MODIFIERS_OPT, TokenType.INTERFACE, TokenType.IDENTIFIER, TokenType.EXTENDS_INTERFACES_OPT, TokenType.INTERFACE_BODY)
      )
    );
    productions.put(
      TokenType.EXTENDS_INTERFACES,
      Arrays.asList(
        Arrays.asList(TokenType.EXTENDS, TokenType.INTERFACE_TYPE),
        Arrays.asList(TokenType.EXTENDS_INTERFACES, TokenType.COMMA, TokenType.INTERFACE_TYPE)
      )
    );
    productions.put(
      TokenType.INTERFACE_MEMBER_DECLARATIONS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.INTERFACE_MEMBER_DECLARATIONS)
      )
    );
    productions.put(
      TokenType.INTERFACE_BODY,
      Arrays.asList(
        Arrays.asList(TokenType.OPEN_CBR, TokenType.INTERFACE_MEMBER_DECLARATIONS_OPT, TokenType.CLOSE_CBR)
      )
    );
    productions.put(
      TokenType.INTERFACE_MEMBER_DECLARATIONS,
      Arrays.asList(
        Arrays.asList(TokenType.INTERFACE_MEMBER_DECLARATION),
        Arrays.asList(TokenType.INTERFACE_MEMBER_DECLARATIONS, TokenType.INTERFACE_MEMBER_DECLARATION)
      )
    );
    productions.put(
      TokenType.INTERFACE_MEMBER_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.CONSTANT_DECLARATION),
        Arrays.asList(TokenType.ABSTRACT_METHOD_DECLARATION)
      )
    );
    productions.put(
      TokenType.CONSTANT_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.FIELD_DECLARATION)
      )
    );
    productions.put(
      TokenType.ABSTRACT_METHOD_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.METHOD_HEADER, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.VARIABLE_INITIALIZERS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.VARIABLE_INITIALIZERS)
      )
    );
    productions.put(
      TokenType.COMMA_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.COMMA)
      )
    );
    productions.put(
      TokenType.ARRAY_INITIALIZER,
      Arrays.asList(
        Arrays.asList(TokenType.OPEN_CBR, TokenType.VARIABLE_INITIALIZERS_OPT, TokenType.COMMA_OPT, TokenType.CLOSE_CBR)
      )
    );
    productions.put(
      TokenType.VARIABLE_INITIALIZERS,
      Arrays.asList(
        Arrays.asList(TokenType.VARIABLE_INITIALIZER),
        Arrays.asList(TokenType.VARIABLE_INITIALIZERS, TokenType.COMMA, TokenType.VARIABLE_INITIALIZER)
      )
    );
    productions.put(
      TokenType.BLOCK,
      Arrays.asList(
        Arrays.asList(TokenType.OPEN_CBR, TokenType.BLOCK_STATEMENTS_OPT, TokenType.CLOSE_CBR)
      )
    );
    productions.put(
      TokenType.BLOCK_STATEMENTS,
      Arrays.asList(
        Arrays.asList(TokenType.BLOCK_STATEMENT),
        Arrays.asList(TokenType.BLOCK_STATEMENTS, TokenType.BLOCK_STATEMENT)
      )
    );
    productions.put(
      TokenType.BLOCK_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.LOCAL_VARIABLE_DECLARATION_STATEMENT),
        Arrays.asList(TokenType.STATEMENT)
      )
    );
    productions.put(
      TokenType.LOCAL_VARIABLE_DECLARATION_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.LOCAL_VARIABLE_DECLARATION, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.LOCAL_VARIABLE_DECLARATION,
      Arrays.asList(
        Arrays.asList(TokenType.TYPE, TokenType.VARIABLE_DECLARATORS)
      )
    );
    productions.put(
      TokenType.STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT),
        Arrays.asList(TokenType.LABELED_STATEMENT),
        Arrays.asList(TokenType.IF_THEN_STATEMENT),
        Arrays.asList(TokenType.IF_THEN_ELSE_STATEMENT),
        Arrays.asList(TokenType.WHILE_STATEMENT),
        Arrays.asList(TokenType.FOR_STATEMENT)
      )
    );
    productions.put(
      TokenType.STATEMENT_NO_SHORT_IF,
      Arrays.asList(
        Arrays.asList(TokenType.STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT),
        Arrays.asList(TokenType.LABELED_STATEMENT_NO_SHORT_IF),
        Arrays.asList(TokenType.IF_THEN_ELSE_STATEMENT_NO_SHORT_IF),
        Arrays.asList(TokenType.WHILE_STATEMENT_NO_SHORT_IF),
        Arrays.asList(TokenType.FOR_STATEMENT_NO_SHORT_IF)
      )
    );
    productions.put(
      TokenType.STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.BLOCK),
        Arrays.asList(TokenType.EMPTY_STATEMENT),
        Arrays.asList(TokenType.EXPRESSION_STATEMENT),
        Arrays.asList(TokenType.SWITCH_STATEMENT),
        Arrays.asList(TokenType.DO_STATEMENT),
        Arrays.asList(TokenType.BREAK_STATEMENT),
        Arrays.asList(TokenType.CONTINUE_STATEMENT),
        Arrays.asList(TokenType.RETURN_STATEMENT),
        Arrays.asList(TokenType.SYNCHRONIZED_STATEMENT),
        Arrays.asList(TokenType.THROW_STATEMENT),
        Arrays.asList(TokenType.TRY_STATEMENT)
      )
    );
    productions.put(
      TokenType.EMPTY_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.LABELED_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.IDENTIFIER, TokenType.COLON, TokenType.STATEMENT)
      )
    );
    productions.put(
      TokenType.LABELED_STATEMENT_NO_SHORT_IF,
      Arrays.asList(
        Arrays.asList(TokenType.IDENTIFIER, TokenType.COLON, TokenType.STATEMENT_NO_SHORT_IF)
      )
    );
    productions.put(
      TokenType.EXPRESSION_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.STATEMENT_EXPRESSION, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.STATEMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.ASSIGNMENT),
        Arrays.asList(TokenType.PRE_INCREMENT_EXPRESSION),
        Arrays.asList(TokenType.PRE_DECREMENT_EXPRESSION),
        Arrays.asList(TokenType.POST_INCREMENT_EXPRESSION),
        Arrays.asList(TokenType.POST_DECREMENT_EXPRESSION),
        Arrays.asList(TokenType.METHOD_INVOCATION),
        Arrays.asList(TokenType.CLASS_INSTANCE_CREATION_EXPRESSION)
      )
    );
    productions.put(
      TokenType.IF_THEN_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.IF, TokenType.OPEN_BR, TokenType.EXPRESSION, TokenType.CLOSE_BR, TokenType.STATEMENT)
      )
    );
    productions.put(
      TokenType.IF_THEN_ELSE_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.IF, TokenType.OPEN_BR, TokenType.EXPRESSION, TokenType.CLOSE_BR, TokenType.STATEMENT_NO_SHORT_IF, TokenType.ELSE, TokenType.STATEMENT)
      )
    );
    productions.put(
      TokenType.IF_THEN_ELSE_STATEMENT_NO_SHORT_IF,
      Arrays.asList(
        Arrays.asList(TokenType.IF, TokenType.OPEN_BR, TokenType.EXPRESSION, TokenType.CLOSE_BR, TokenType.STATEMENT_NO_SHORT_IF, TokenType.ELSE, TokenType.STATEMENT_NO_SHORT_IF)
      )
    );
    productions.put(
      TokenType.SWITCH_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.SWITCH, TokenType.OPEN_BR, TokenType.EXPRESSION, TokenType.CLOSE_BR, TokenType.SWITCH_BLOCK)
      )
    );
    productions.put(
      TokenType.SWITCH_BLOCK_STATEMENT_GROUPS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.SWITCH_BLOCK_STATEMENT_GROUPS)
      )
    );
    productions.put(
      TokenType.SWITCH_LABELS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.SWITCH_LABELS)
      )
    );
    productions.put(
      TokenType.SWITCH_BLOCK,
      Arrays.asList(
        Arrays.asList(TokenType.OPEN_CBR, TokenType.SWITCH_BLOCK_STATEMENT_GROUPS_OPT, TokenType.SWITCH_LABELS_OPT, TokenType.CLOSE_CBR)
      )
    );
    productions.put(
      TokenType.SWITCH_BLOCK_STATEMENT_GROUPS,
      Arrays.asList(
        Arrays.asList(TokenType.SWITCH_BLOCK_STATEMENT_GROUP),
        Arrays.asList(TokenType.SWITCH_BLOCK_STATEMENT_GROUPS, TokenType.SWITCH_BLOCK_STATEMENT_GROUP)
      )
    );
    productions.put(
      TokenType.SWITCH_BLOCK_STATEMENT_GROUP,
      Arrays.asList(
        Arrays.asList(TokenType.SWITCH_LABELS, TokenType.BLOCK_STATEMENTS)
      )
    );
    productions.put(
      TokenType.SWITCH_LABELS,
      Arrays.asList(
        Arrays.asList(TokenType.SWITCH_LABEL),
        Arrays.asList(TokenType.SWITCH_LABELS, TokenType.SWITCH_LABEL)
      )
    );
    productions.put(
      TokenType.SWITCH_LABEL,
      Arrays.asList(
        Arrays.asList(TokenType.CASE, TokenType.CONSTANT_EXPRESSION, TokenType.COLON),
        Arrays.asList(TokenType.DEFAULT, TokenType.COLON)
      )
    );
    productions.put(
      TokenType.WHILE_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.WHILE, TokenType.OPEN_BR, TokenType.EXPRESSION, TokenType.CLOSE_BR, TokenType.STATEMENT)
      )
    );
    productions.put(
      TokenType.WHILE_STATEMENT_NO_SHORT_IF,
      Arrays.asList(
        Arrays.asList(TokenType.WHILE, TokenType.OPEN_BR, TokenType.EXPRESSION, TokenType.CLOSE_BR, TokenType.STATEMENT_NO_SHORT_IF)
      )
    );
    productions.put(
      TokenType.DO_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.DO, TokenType.STATEMENT, TokenType.WHILE, TokenType.OPEN_BR, TokenType.EXPRESSION, TokenType.CLOSE_BR, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.FOR_INIT_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.FOR_INIT)
      )
    );
    productions.put(
      TokenType.EXPRESSION_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.EXPRESSION)
      )
    );
    productions.put(
      TokenType.FOR_UPDATE_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.FOR_UPDATE)
      )
    );
    productions.put(
      TokenType.FOR_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.FOR, TokenType.OPEN_BR, TokenType.FOR_INIT_OPT, TokenType.SEMICOLON, TokenType.EXPRESSION_OPT, TokenType.SEMICOLON, TokenType.FOR_UPDATE_OPT, TokenType.CLOSE_BR, TokenType.STATEMENT)
      )
    );
    productions.put(
      TokenType.FOR_STATEMENT_NO_SHORT_IF,
      Arrays.asList(
        Arrays.asList(TokenType.FOR, TokenType.OPEN_BR, TokenType.FOR_INIT_OPT, TokenType.SEMICOLON, TokenType.EXPRESSION_OPT, TokenType.SEMICOLON, TokenType.FOR_UPDATE_OPT, TokenType.CLOSE_BR, TokenType.STATEMENT_NO_SHORT_IF)
      )
    );
    productions.put(
      TokenType.FOR_INIT,
      Arrays.asList(
        Arrays.asList(TokenType.STATEMENT_EXPRESSION_LIST),
        Arrays.asList(TokenType.LOCAL_VARIABLE_DECLARATION)
      )
    );
    productions.put(
      TokenType.FOR_UPDATE,
      Arrays.asList(
        Arrays.asList(TokenType.STATEMENT_EXPRESSION_LIST)
      )
    );
    productions.put(
      TokenType.STATEMENT_EXPRESSION_LIST,
      Arrays.asList(
        Arrays.asList(TokenType.STATEMENT_EXPRESSION),
        Arrays.asList(TokenType.STATEMENT_EXPRESSION_LIST, TokenType.COMMA, TokenType.STATEMENT_EXPRESSION)
      )
    );
    productions.put(
      TokenType.IDENTIFIER_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.IDENTIFIER)
      )
    );
    productions.put(
      TokenType.BREAK_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.BREAK, TokenType.IDENTIFIER_OPT, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.CONTINUE_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.CONTINUE, TokenType.IDENTIFIER_OPT, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.RETURN_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.RETURN, TokenType.EXPRESSION_OPT, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.THROW_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.THROW, TokenType.EXPRESSION, TokenType.SEMICOLON)
      )
    );
    productions.put(
      TokenType.SYNCHRONIZED_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.SYNCHRONIZED, TokenType.OPEN_BR, TokenType.EXPRESSION, TokenType.CLOSE_BR, TokenType.BLOCK)
      )
    );
    productions.put(
      TokenType.CATCHES_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.CATCHES)
      )
    );
    productions.put(
      TokenType.TRY_STATEMENT,
      Arrays.asList(
        Arrays.asList(TokenType.TRY, TokenType.BLOCK, TokenType.CATCHES),
        Arrays.asList(TokenType.TRY, TokenType.BLOCK, TokenType.CATCHES_OPT, TokenType.FINALLY_CLAUSE)
      )
    );
    productions.put(
      TokenType.CATCHES,
      Arrays.asList(
        Arrays.asList(TokenType.CATCH_CLAUSE),
        Arrays.asList(TokenType.CATCHES, TokenType.CATCH_CLAUSE)
      )
    );
    productions.put(
      TokenType.CATCH_CLAUSE,
      Arrays.asList(
        Arrays.asList(TokenType.CATCH, TokenType.OPEN_BR, TokenType.FORMAL_PARAMETER, TokenType.CLOSE_BR, TokenType.BLOCK)
      )
    );
    productions.put(
      TokenType.FINALLY_CLAUSE,
      Arrays.asList(
        Arrays.asList(TokenType.FINALLY, TokenType.BLOCK)
      )
    );
    productions.put(
      TokenType.PRIMARY,
      Arrays.asList(
        Arrays.asList(TokenType.PRIMARY_NO_NEW_ARRAY),
        Arrays.asList(TokenType.ARRAY_CREATION_EXPRESSION)
      )
    );
    productions.put(
      TokenType.PRIMARY_NO_NEW_ARRAY,
      Arrays.asList(
        Arrays.asList(TokenType.LITERAL),
        Arrays.asList(TokenType.THIS),
        Arrays.asList(TokenType.OPEN_BR, TokenType.EXPRESSION, TokenType.CLOSE_BR),
        Arrays.asList(TokenType.CLASS_INSTANCE_CREATION_EXPRESSION),
        Arrays.asList(TokenType.FIELD_ACCESS),
        Arrays.asList(TokenType.METHOD_INVOCATION),
        Arrays.asList(TokenType.ARRAY_ACCESS)
      )
    );
    productions.put(
      TokenType.CLASS_INSTANCE_CREATION_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.NEW, TokenType.CLASS_TYPE, TokenType.OPEN_BR, TokenType.ARGUMENT_LIST_OPT, TokenType.CLOSE_BR)
      )
    );
    productions.put(
      TokenType.ARGUMENT_LIST,
      Arrays.asList(
        Arrays.asList(TokenType.EXPRESSION),
        Arrays.asList(TokenType.ARGUMENT_LIST, TokenType.COMMA, TokenType.EXPRESSION)
      )
    );
    productions.put(
      TokenType.DIMS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TokenType.DIMS)
      )
    );
    productions.put(
      TokenType.ARRAY_CREATION_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.NEW, TokenType.PRIMITIVE_TYPE, TokenType.DIM_EXPRS, TokenType.DIMS_OPT),
        Arrays.asList(TokenType.NEW, TokenType.CLASS_OR_INTERFACE_TYPE, TokenType.DIM_EXPRS, TokenType.DIMS_OPT)
      )
    );
    productions.put(
      TokenType.DIM_EXPRS,
      Arrays.asList(
        Arrays.asList(TokenType.DIM_EXPR),
        Arrays.asList(TokenType.DIM_EXPRS, TokenType.DIM_EXPR)
      )
    );
    productions.put(
      TokenType.DIM_EXPR,
      Arrays.asList(
        Arrays.asList(TokenType.OPEN_SBR, TokenType.EXPRESSION, TokenType.CLOSE_SBR)
      )
    );
    productions.put(
      TokenType.DIMS,
      Arrays.asList(
        Arrays.asList(TokenType.OPEN_SBR, TokenType.CLOSE_SBR),
        Arrays.asList(TokenType.DIMS, TokenType.OPEN_SBR, TokenType.CLOSE_SBR)
      )
    );
    productions.put(
      TokenType.FIELD_ACCESS,
      Arrays.asList(
        Arrays.asList(TokenType.PRIMARY, TokenType.DOT, TokenType.IDENTIFIER),
        Arrays.asList(TokenType.SUPER, TokenType.DOT, TokenType.IDENTIFIER)
      )
    );
    productions.put(
      TokenType.METHOD_INVOCATION,
      Arrays.asList(
        Arrays.asList(TokenType.NAME, TokenType.OPEN_BR, TokenType.ARGUMENT_LIST_OPT, TokenType.CLOSE_BR),
        Arrays.asList(TokenType.PRIMARY, TokenType.DOT, TokenType.IDENTIFIER, TokenType.OPEN_BR, TokenType.ARGUMENT_LIST_OPT, TokenType.CLOSE_BR),
        Arrays.asList(TokenType.SUPER, TokenType.DOT, TokenType.IDENTIFIER, TokenType.OPEN_BR, TokenType.ARGUMENT_LIST_OPT, TokenType.CLOSE_BR)
      )
    );
    productions.put(
      TokenType.ARRAY_ACCESS,
      Arrays.asList(
        Arrays.asList(TokenType.NAME, TokenType.OPEN_SBR, TokenType.EXPRESSION, TokenType.CLOSE_SBR),
        Arrays.asList(TokenType.PRIMARY_NO_NEW_ARRAY, TokenType.OPEN_SBR, TokenType.EXPRESSION, TokenType.CLOSE_SBR)
      )
    );
    productions.put(
      TokenType.POSTFIX_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.PRIMARY),
        Arrays.asList(TokenType.NAME),
        Arrays.asList(TokenType.POST_INCREMENT_EXPRESSION),
        Arrays.asList(TokenType.POST_DECREMENT_EXPRESSION)
      )
    );
    productions.put(
      TokenType.POST_INCREMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.POSTFIX_EXPRESSION, TokenType.ASSIGN_INCREMENT)
      )
    );
    productions.put(
      TokenType.POST_DECREMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.POSTFIX_EXPRESSION, TokenType.ASSIGN_DECREMENT)
      )
    );
    productions.put(
      TokenType.UNARY_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.PRE_INCREMENT_EXPRESSION),
        Arrays.asList(TokenType.PRE_DECREMENT_EXPRESSION),
        Arrays.asList(TokenType.OP_PLUS, TokenType.UNARY_EXPRESSION),
        Arrays.asList(TokenType.OP_MINUS, TokenType.UNARY_EXPRESSION),
        Arrays.asList(TokenType.UNARY_EXPRESSION_NOT_PLUS_MINUS)
      )
    );
    productions.put(
      TokenType.PRE_INCREMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.ASSIGN_INCREMENT, TokenType.UNARY_EXPRESSION)
      )
    );
    productions.put(
      TokenType.PRE_DECREMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.ASSIGN_DECREMENT, TokenType.UNARY_EXPRESSION)
      )
    );
    productions.put(
      TokenType.UNARY_EXPRESSION_NOT_PLUS_MINUS,
      Arrays.asList(
        Arrays.asList(TokenType.POSTFIX_EXPRESSION),
        Arrays.asList(TokenType.BITWISE_NOT, TokenType.UNARY_EXPRESSION),
        Arrays.asList(TokenType.BOOL_OP_NOT, TokenType.UNARY_EXPRESSION),
        Arrays.asList(TokenType.CAST_EXPRESSION)
      )
    );
    productions.put(
      TokenType.CAST_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.OPEN_BR, TokenType.PRIMITIVE_TYPE, TokenType.DIMS_OPT, TokenType.CLOSE_BR, TokenType.UNARY_EXPRESSION),
        Arrays.asList(TokenType.OPEN_BR, TokenType.EXPRESSION, TokenType.CLOSE_BR, TokenType.UNARY_EXPRESSION_NOT_PLUS_MINUS),
        Arrays.asList(TokenType.OPEN_BR, TokenType.NAME, TokenType.DIMS, TokenType.CLOSE_BR, TokenType.UNARY_EXPRESSION_NOT_PLUS_MINUS)
      )
    );
    productions.put(
      TokenType.MULTIPLICATIVE_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.UNARY_EXPRESSION),
        Arrays.asList(TokenType.MULTIPLICATIVE_EXPRESSION, TokenType.STAR, TokenType.UNARY_EXPRESSION),
        Arrays.asList(TokenType.MULTIPLICATIVE_EXPRESSION, TokenType.OP_DIV, TokenType.UNARY_EXPRESSION),
        Arrays.asList(TokenType.MULTIPLICATIVE_EXPRESSION, TokenType.OP_REMAINDER, TokenType.UNARY_EXPRESSION)
      )
    );
    productions.put(
      TokenType.ADDITIVE_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.MULTIPLICATIVE_EXPRESSION),
        Arrays.asList(TokenType.ADDITIVE_EXPRESSION, TokenType.OP_PLUS, TokenType.MULTIPLICATIVE_EXPRESSION),
        Arrays.asList(TokenType.ADDITIVE_EXPRESSION, TokenType.OP_MINUS, TokenType.MULTIPLICATIVE_EXPRESSION)
      )
    );
    productions.put(
      TokenType.SHIFT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.ADDITIVE_EXPRESSION),
        Arrays.asList(TokenType.SHIFT_EXPRESSION, TokenType.OP_LEFT_SHIFT, TokenType.ADDITIVE_EXPRESSION),
        Arrays.asList(TokenType.SHIFT_EXPRESSION, TokenType.OP_RIGHT_SHIFT, TokenType.ADDITIVE_EXPRESSION),
        Arrays.asList(TokenType.SHIFT_EXPRESSION, TokenType.OP_UNSIGNED_RIGHT_SHIFT, TokenType.ADDITIVE_EXPRESSION)
      )
    );
    productions.put(
      TokenType.RELATIONAL_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.SHIFT_EXPRESSION),
        Arrays.asList(TokenType.RELATIONAL_EXPRESSION, TokenType.COMP_LESS_THAN, TokenType.SHIFT_EXPRESSION),
        Arrays.asList(TokenType.RELATIONAL_EXPRESSION, TokenType.COMP_GREATER_THAN, TokenType.SHIFT_EXPRESSION),
        Arrays.asList(TokenType.RELATIONAL_EXPRESSION, TokenType.COMP_LESS_THAN_EQ, TokenType.SHIFT_EXPRESSION),
        Arrays.asList(TokenType.RELATIONAL_EXPRESSION, TokenType.COMP_GREATER_THAN_EQ, TokenType.SHIFT_EXPRESSION),
        Arrays.asList(TokenType.RELATIONAL_EXPRESSION, TokenType.INSTANCEOF, TokenType.REFERENCE_TYPE)
      )
    );
    productions.put(
      TokenType.EQUALITY_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.RELATIONAL_EXPRESSION),
        Arrays.asList(TokenType.EQUALITY_EXPRESSION, TokenType.COMP_EQ, TokenType.RELATIONAL_EXPRESSION),
        Arrays.asList(TokenType.EQUALITY_EXPRESSION, TokenType.COMP_NOT_EQ, TokenType.RELATIONAL_EXPRESSION)
      )
    );
    productions.put(
      TokenType.AND_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.EQUALITY_EXPRESSION),
        Arrays.asList(TokenType.AND_EXPRESSION, TokenType.BITWISE_AND, TokenType.EQUALITY_EXPRESSION)
      )
    );
    productions.put(
      TokenType.EXCLUSIVE_OR_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.AND_EXPRESSION),
        Arrays.asList(TokenType.EXCLUSIVE_OR_EXPRESSION, TokenType.BITWISE_XOR, TokenType.AND_EXPRESSION)
      )
    );
    productions.put(
      TokenType.INCLUSIVE_OR_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.EXCLUSIVE_OR_EXPRESSION),
        Arrays.asList(TokenType.INCLUSIVE_OR_EXPRESSION, TokenType.BITWISE_OR, TokenType.EXCLUSIVE_OR_EXPRESSION)
      )
    );
    productions.put(
      TokenType.CONDITIONAL_AND_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.INCLUSIVE_OR_EXPRESSION),
        Arrays.asList(TokenType.CONDITIONAL_AND_EXPRESSION, TokenType.BOOL_OP_AND, TokenType.INCLUSIVE_OR_EXPRESSION)
      )
    );
    productions.put(
      TokenType.CONDITIONAL_OR_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.CONDITIONAL_AND_EXPRESSION),
        Arrays.asList(TokenType.CONDITIONAL_OR_EXPRESSION, TokenType.BOOL_OP_OR, TokenType.CONDITIONAL_AND_EXPRESSION)
      )
    );
    productions.put(
      TokenType.CONDITIONAL_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.CONDITIONAL_OR_EXPRESSION),
        Arrays.asList(TokenType.CONDITIONAL_OR_EXPRESSION, TokenType.QUESTION_MARK, TokenType.EXPRESSION, TokenType.COLON, TokenType.CONDITIONAL_EXPRESSION)
      )
    );
    productions.put(
      TokenType.ASSIGNMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.CONDITIONAL_EXPRESSION),
        Arrays.asList(TokenType.ASSIGNMENT)
      )
    );
    productions.put(
      TokenType.ASSIGNMENT,
      Arrays.asList(
        Arrays.asList(TokenType.LEFT_HAND_SIDE, TokenType.ASSIGNMENT_OPERATOR, TokenType.ASSIGNMENT_EXPRESSION)
      )
    );
    productions.put(
      TokenType.LEFT_HAND_SIDE,
      Arrays.asList(
        Arrays.asList(TokenType.NAME),
        Arrays.asList(TokenType.FIELD_ACCESS),
        Arrays.asList(TokenType.ARRAY_ACCESS)
      )
    );
    productions.put(
      TokenType.ASSIGNMENT_OPERATOR,
      Arrays.asList(
        Arrays.asList(TokenType.ASSIGN)
      )
    );
    productions.put(
      TokenType.EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.ASSIGNMENT_EXPRESSION)
      )
    );
    productions.put(
      TokenType.CONSTANT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TokenType.EXPRESSION)
      )
    );
    HashSet<TokenType> terminals = new HashSet(TerminalToken.getAllTokens());
    HashSet<TokenType> nonterminals = new HashSet(NonterminalToken.getAllTokens());
    grammar = new Grammar(
      terminals,
      nonterminals,
      productions,
      TokenType.GOAL
    );
  }
}
