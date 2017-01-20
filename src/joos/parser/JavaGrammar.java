package joos.parser;

import joos.commons.NonterminalToken;
import joos.commons.TerminalToken;
import joos.commons.Token;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import joos.parser.Grammar;


public final class JavaGrammar {
  public static final Grammar grammar;
  private JavaGrammar() { }
  static {
    HashMap<NonterminalToken, List<List<Token>>> productions = new HashMap();
    productions.put(
      NonterminalToken.GOAL,
      Arrays.asList(
        Arrays.asList(NonterminalToken.COMPILATION_UNIT)
      )
    );
    productions.put(
      NonterminalToken.BOOLEAN_LITERAL,
      Arrays.asList(
        Arrays.asList(TerminalToken.BOOLEAN_LITERAL_TRUE),
        Arrays.asList(TerminalToken.BOOLEAN_LITERAL_FALSE)
      )
    );
    productions.put(
      NonterminalToken.LITERAL,
      Arrays.asList(
        Arrays.asList(TerminalToken.INTEGER_LITERAL),
        Arrays.asList(TerminalToken.FLOATING_POINT_LITERAL),
        Arrays.asList(NonterminalToken.BOOLEAN_LITERAL),
        Arrays.asList(TerminalToken.CHAR_LITERAL),
        Arrays.asList(TerminalToken.STRING_LITERAL),
        Arrays.asList(TerminalToken.NULL_LITERAL)
      )
    );
    productions.put(
      NonterminalToken.TYPE,
      Arrays.asList(
        Arrays.asList(NonterminalToken.PRIMITIVE_TYPE),
        Arrays.asList(NonterminalToken.REFERENCE_TYPE)
      )
    );
    productions.put(
      NonterminalToken.PRIMITIVE_TYPE,
      Arrays.asList(
        Arrays.asList(NonterminalToken.NUMERIC_TYPE),
        Arrays.asList(TerminalToken.BOOLEAN),
        Arrays.asList()
      )
    );
    productions.put(
      NonterminalToken.NUMERIC_TYPE,
      Arrays.asList(
        Arrays.asList(NonterminalToken.INTEGRAL_TYPE),
        Arrays.asList(NonterminalToken.FLOATING_POINT_TYPE)
      )
    );
    productions.put(
      NonterminalToken.INTEGRAL_TYPE,
      Arrays.asList(
        Arrays.asList(TerminalToken.BYTE),
        Arrays.asList(TerminalToken.SHORT),
        Arrays.asList(TerminalToken.INT),
        Arrays.asList(TerminalToken.LONG),
        Arrays.asList(TerminalToken.CHAR)
      )
    );
    productions.put(
      NonterminalToken.FLOATING_POINT_TYPE,
      Arrays.asList(
        Arrays.asList(TerminalToken.FLOAT),
        Arrays.asList(TerminalToken.DOUBLE)
      )
    );
    productions.put(
      NonterminalToken.REFERENCE_TYPE,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CLASS_OR_INTERFACE_TYPE),
        Arrays.asList(NonterminalToken.ARRAY_TYPE)
      )
    );
    productions.put(
      NonterminalToken.CLASS_OR_INTERFACE_TYPE,
      Arrays.asList(
        Arrays.asList(NonterminalToken.NAME)
      )
    );
    productions.put(
      NonterminalToken.CLASS_TYPE,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CLASS_OR_INTERFACE_TYPE)
      )
    );
    productions.put(
      NonterminalToken.INTERFACE_TYPE,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CLASS_OR_INTERFACE_TYPE)
      )
    );
    productions.put(
      NonterminalToken.ARRAY_TYPE,
      Arrays.asList(
        Arrays.asList(NonterminalToken.PRIMITIVE_TYPE, TerminalToken.OPEN_SBR, TerminalToken.CLOSE_SBR),
        Arrays.asList(NonterminalToken.NAME, TerminalToken.OPEN_SBR, TerminalToken.CLOSE_SBR),
        Arrays.asList(NonterminalToken.ARRAY_TYPE, TerminalToken.OPEN_SBR, TerminalToken.CLOSE_SBR)
      )
    );
    productions.put(
      NonterminalToken.NAME,
      Arrays.asList(
        Arrays.asList(NonterminalToken.SIMPLE_NAME),
        Arrays.asList(NonterminalToken.QUALIFIED_NAME)
      )
    );
    productions.put(
      NonterminalToken.SIMPLE_NAME,
      Arrays.asList(
        Arrays.asList(TerminalToken.IDENTIFIER)
      )
    );
    productions.put(
      NonterminalToken.QUALIFIED_NAME,
      Arrays.asList(
        Arrays.asList(NonterminalToken.NAME, TerminalToken.DOT, TerminalToken.IDENTIFIER)
      )
    );
    productions.put(
      NonterminalToken.PACKAGE_DECLARATION_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.PACKAGE_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.IMPORT_DECLARATIONS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.IMPORT_DECLARATIONS)
      )
    );
    productions.put(
      NonterminalToken.TYPE_DECLARATIONS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.TYPE_DECLARATIONS)
      )
    );
    productions.put(
      NonterminalToken.COMPILATION_UNIT,
      Arrays.asList(
        Arrays.asList(NonterminalToken.PACKAGE_DECLARATION_OPT, NonterminalToken.IMPORT_DECLARATIONS_OPT, NonterminalToken.TYPE_DECLARATIONS_OPT)
      )
    );
    productions.put(
      NonterminalToken.IMPORT_DECLARATIONS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.IMPORT_DECLARATION),
        Arrays.asList(NonterminalToken.IMPORT_DECLARATIONS, NonterminalToken.IMPORT_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.TYPE_DECLARATIONS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.TYPE_DECLARATION),
        Arrays.asList(NonterminalToken.TYPE_DECLARATIONS, NonterminalToken.TYPE_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.PACKAGE_DECLARATION,
      Arrays.asList(
        Arrays.asList(TerminalToken.PACKAGE, NonterminalToken.NAME, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.IMPORT_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.SINGLE_TYPE_IMPORT_DECLARATION),
        Arrays.asList(NonterminalToken.TYPE_IMPORT_ON_DEMAND_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.SINGLE_TYPE_IMPORT_DECLARATION,
      Arrays.asList(
        Arrays.asList(TerminalToken.IMPORT, NonterminalToken.NAME, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.TYPE_IMPORT_ON_DEMAND_DECLARATION,
      Arrays.asList(
        Arrays.asList(TerminalToken.IMPORT, NonterminalToken.NAME, TerminalToken.DOT, TerminalToken.STAR, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.TYPE_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CLASS_DECLARATION),
        Arrays.asList(NonterminalToken.INTERFACE_DECLARATION),
        Arrays.asList(TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.MODIFIERS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.MODIFIER),
        Arrays.asList(NonterminalToken.MODIFIERS, NonterminalToken.MODIFIER)
      )
    );
    productions.put(
      NonterminalToken.MODIFIER,
      Arrays.asList(
        Arrays.asList(TerminalToken.PUBLIC),
        Arrays.asList(TerminalToken.PROTECTED),
        Arrays.asList(TerminalToken.PRIVATE),
        Arrays.asList(TerminalToken.STATIC),
        Arrays.asList(TerminalToken.ABSTRACT),
        Arrays.asList(TerminalToken.FINAL),
        Arrays.asList(TerminalToken.NATIVE),
        Arrays.asList(TerminalToken.SYNCHRONIZED),
        Arrays.asList(TerminalToken.TRANSIENT),
        Arrays.asList(TerminalToken.VOLATILE)
      )
    );
    productions.put(
      NonterminalToken.MODIFIERS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.MODIFIERS)
      )
    );
    productions.put(
      NonterminalToken.SUPER_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.SUPER)
      )
    );
    productions.put(
      NonterminalToken.INTERFACES_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.INTERFACES)
      )
    );
    productions.put(
      NonterminalToken.CLASS_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.MODIFIERS_OPT, TerminalToken.CLASS, TerminalToken.IDENTIFIER, NonterminalToken.SUPER_OPT, NonterminalToken.INTERFACES_OPT, NonterminalToken.CLASS_BODY)
      )
    );
    productions.put(
      NonterminalToken.SUPER,
      Arrays.asList(
        Arrays.asList(TerminalToken.EXTENDS, NonterminalToken.CLASS_TYPE)
      )
    );
    productions.put(
      NonterminalToken.INTERFACES,
      Arrays.asList(
        Arrays.asList(TerminalToken.IMPLEMENTS, NonterminalToken.INTERFACE_TYPE_LIST)
      )
    );
    productions.put(
      NonterminalToken.INTERFACE_TYPE_LIST,
      Arrays.asList(
        Arrays.asList(NonterminalToken.INTERFACE_TYPE),
        Arrays.asList(NonterminalToken.INTERFACE_TYPE_LIST, TerminalToken.COMMA, NonterminalToken.INTERFACE_TYPE)
      )
    );
    productions.put(
      NonterminalToken.CLASS_BODY_DECLARATIONS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.CLASS_BODY_DECLARATIONS)
      )
    );
    productions.put(
      NonterminalToken.CLASS_BODY,
      Arrays.asList(
        Arrays.asList(TerminalToken.OPEN_CBR, NonterminalToken.CLASS_BODY_DECLARATIONS_OPT, TerminalToken.CLOSE_CBR)
      )
    );
    productions.put(
      NonterminalToken.CLASS_BODY_DECLARATIONS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CLASS_BODY_DECLARATION),
        Arrays.asList(NonterminalToken.CLASS_BODY_DECLARATIONS, NonterminalToken.CLASS_BODY_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.CLASS_BODY_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CLASS_MEMBER_DECLARATION),
        Arrays.asList(NonterminalToken.STATIC_INITIALIZER),
        Arrays.asList(NonterminalToken.CONSTRUCTOR_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.CLASS_MEMBER_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.FIELD_DECLARATION),
        Arrays.asList(NonterminalToken.METHOD_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.FIELD_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.MODIFIERS_OPT, NonterminalToken.TYPE, NonterminalToken.VARIABLE_DECLARATORS, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.VARIABLE_DECLARATORS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.VARIABLE_DECLARATOR),
        Arrays.asList(NonterminalToken.VARIABLE_DECLARATORS, TerminalToken.COMMA, NonterminalToken.VARIABLE_DECLARATOR)
      )
    );
    productions.put(
      NonterminalToken.VARIABLE_DECLARATOR,
      Arrays.asList(
        Arrays.asList(NonterminalToken.VARIABLE_DECLARATOR_ID),
        Arrays.asList(NonterminalToken.VARIABLE_DECLARATOR_ID, TerminalToken.ASSIGN, NonterminalToken.VARIABLE_INITIALIZER)
      )
    );
    productions.put(
      NonterminalToken.VARIABLE_DECLARATOR_ID,
      Arrays.asList(
        Arrays.asList(TerminalToken.IDENTIFIER),
        Arrays.asList(NonterminalToken.VARIABLE_DECLARATOR_ID, TerminalToken.OPEN_SBR, TerminalToken.CLOSE_SBR)
      )
    );
    productions.put(
      NonterminalToken.VARIABLE_INITIALIZER,
      Arrays.asList(
        Arrays.asList(NonterminalToken.EXPRESSION),
        Arrays.asList(NonterminalToken.ARRAY_INITIALIZER)
      )
    );
    productions.put(
      NonterminalToken.METHOD_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.METHOD_HEADER, NonterminalToken.METHOD_BODY)
      )
    );
    productions.put(
      NonterminalToken.THROWS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.THROWS)
      )
    );
    productions.put(
      NonterminalToken.METHOD_HEADER,
      Arrays.asList(
        Arrays.asList(NonterminalToken.MODIFIERS_OPT, NonterminalToken.TYPE, NonterminalToken.METHOD_DECLARATOR, NonterminalToken.THROWS_OPT),
        Arrays.asList(NonterminalToken.MODIFIERS_OPT, TerminalToken.VOID, NonterminalToken.METHOD_DECLARATOR, NonterminalToken.THROWS_OPT)
      )
    );
    productions.put(
      NonterminalToken.FORMAL_PARAMETER_LIST_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.FORMAL_PARAMETER_LIST)
      )
    );
    productions.put(
      NonterminalToken.METHOD_DECLARATOR,
      Arrays.asList(
        Arrays.asList(TerminalToken.IDENTIFIER, TerminalToken.OPEN_BR, NonterminalToken.FORMAL_PARAMETER_LIST_OPT, TerminalToken.CLOSE_BR),
        Arrays.asList(NonterminalToken.METHOD_DECLARATOR, TerminalToken.OPEN_SBR, TerminalToken.CLOSE_SBR)
      )
    );
    productions.put(
      NonterminalToken.FORMAL_PARAMETER_LIST,
      Arrays.asList(
        Arrays.asList(NonterminalToken.FORMAL_PARAMETER),
        Arrays.asList(NonterminalToken.FORMAL_PARAMETER_LIST, TerminalToken.COMMA, NonterminalToken.FORMAL_PARAMETER)
      )
    );
    productions.put(
      NonterminalToken.FORMAL_PARAMETER,
      Arrays.asList(
        Arrays.asList(NonterminalToken.TYPE, NonterminalToken.VARIABLE_DECLARATOR_ID)
      )
    );
    productions.put(
      NonterminalToken.THROWS,
      Arrays.asList(
        Arrays.asList(TerminalToken.THROWS, NonterminalToken.CLASS_TYPE_LIST)
      )
    );
    productions.put(
      NonterminalToken.CLASS_TYPE_LIST,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CLASS_TYPE),
        Arrays.asList(NonterminalToken.CLASS_TYPE_LIST, TerminalToken.COMMA, NonterminalToken.CLASS_TYPE)
      )
    );
    productions.put(
      NonterminalToken.METHOD_BODY,
      Arrays.asList(
        Arrays.asList(NonterminalToken.BLOCK),
        Arrays.asList(TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.STATIC_INITIALIZER,
      Arrays.asList(
        Arrays.asList(TerminalToken.STATIC, NonterminalToken.BLOCK)
      )
    );
    productions.put(
      NonterminalToken.CONSTRUCTOR_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.MODIFIERS_OPT, NonterminalToken.CONSTRUCTOR_DECLARATOR, NonterminalToken.THROWS_OPT, NonterminalToken.CONSTRUCTOR_BODY)
      )
    );
    productions.put(
      NonterminalToken.CONSTRUCTOR_DECLARATOR,
      Arrays.asList(
        Arrays.asList(NonterminalToken.SIMPLE_NAME, TerminalToken.OPEN_BR, NonterminalToken.FORMAL_PARAMETER_LIST_OPT, TerminalToken.CLOSE_BR)
      )
    );
    productions.put(
      NonterminalToken.EXPLICIT_CONSTRUCTOR_INVOCATION_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.EXPLICIT_CONSTRUCTOR_INVOCATION)
      )
    );
    productions.put(
      NonterminalToken.BLOCK_STATEMENTS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.BLOCK_STATEMENTS)
      )
    );
    productions.put(
      NonterminalToken.CONSTRUCTOR_BODY,
      Arrays.asList(
        Arrays.asList(TerminalToken.OPEN_CBR, NonterminalToken.EXPLICIT_CONSTRUCTOR_INVOCATION_OPT, NonterminalToken.BLOCK_STATEMENTS_OPT, TerminalToken.CLOSE_CBR)
      )
    );
    productions.put(
      NonterminalToken.ARGUMENT_LIST_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.ARGUMENT_LIST)
      )
    );
    productions.put(
      NonterminalToken.EXPLICIT_CONSTRUCTOR_INVOCATION,
      Arrays.asList(
        Arrays.asList(TerminalToken.THIS, TerminalToken.OPEN_BR, NonterminalToken.ARGUMENT_LIST_OPT, TerminalToken.CLOSE_BR, TerminalToken.SEMICOLON),
        Arrays.asList(TerminalToken.SUPER, TerminalToken.OPEN_BR, NonterminalToken.ARGUMENT_LIST_OPT, TerminalToken.CLOSE_BR, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.EXTENDS_INTERFACES_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.EXTENDS_INTERFACES)
      )
    );
    productions.put(
      NonterminalToken.INTERFACE_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.MODIFIERS_OPT, TerminalToken.INTERFACE, TerminalToken.IDENTIFIER, NonterminalToken.EXTENDS_INTERFACES_OPT, NonterminalToken.INTERFACE_BODY)
      )
    );
    productions.put(
      NonterminalToken.EXTENDS_INTERFACES,
      Arrays.asList(
        Arrays.asList(TerminalToken.EXTENDS, NonterminalToken.INTERFACE_TYPE),
        Arrays.asList(NonterminalToken.EXTENDS_INTERFACES, TerminalToken.COMMA, NonterminalToken.INTERFACE_TYPE)
      )
    );
    productions.put(
      NonterminalToken.INTERFACE_MEMBER_DECLARATIONS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.INTERFACE_MEMBER_DECLARATIONS)
      )
    );
    productions.put(
      NonterminalToken.INTERFACE_BODY,
      Arrays.asList(
        Arrays.asList(TerminalToken.OPEN_CBR, NonterminalToken.INTERFACE_MEMBER_DECLARATIONS_OPT, TerminalToken.CLOSE_CBR)
      )
    );
    productions.put(
      NonterminalToken.INTERFACE_MEMBER_DECLARATIONS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.INTERFACE_MEMBER_DECLARATION),
        Arrays.asList(NonterminalToken.INTERFACE_MEMBER_DECLARATIONS, NonterminalToken.INTERFACE_MEMBER_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.INTERFACE_MEMBER_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CONSTANT_DECLARATION),
        Arrays.asList(NonterminalToken.ABSTRACT_METHOD_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.CONSTANT_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.FIELD_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.ABSTRACT_METHOD_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.METHOD_HEADER, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.VARIABLE_INITIALIZERS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.VARIABLE_INITIALIZERS)
      )
    );
    productions.put(
      NonterminalToken.COMMA_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TerminalToken.COMMA)
      )
    );
    productions.put(
      NonterminalToken.ARRAY_INITIALIZER,
      Arrays.asList(
        Arrays.asList(TerminalToken.OPEN_CBR, NonterminalToken.VARIABLE_INITIALIZERS_OPT, NonterminalToken.COMMA_OPT, TerminalToken.CLOSE_CBR)
      )
    );
    productions.put(
      NonterminalToken.VARIABLE_INITIALIZERS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.VARIABLE_INITIALIZER),
        Arrays.asList(NonterminalToken.VARIABLE_INITIALIZERS, TerminalToken.COMMA, NonterminalToken.VARIABLE_INITIALIZER)
      )
    );
    productions.put(
      NonterminalToken.BLOCK,
      Arrays.asList(
        Arrays.asList(TerminalToken.OPEN_CBR, NonterminalToken.BLOCK_STATEMENTS_OPT, TerminalToken.CLOSE_CBR)
      )
    );
    productions.put(
      NonterminalToken.BLOCK_STATEMENTS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.BLOCK_STATEMENT),
        Arrays.asList(NonterminalToken.BLOCK_STATEMENTS, NonterminalToken.BLOCK_STATEMENT)
      )
    );
    productions.put(
      NonterminalToken.BLOCK_STATEMENT,
      Arrays.asList(
        Arrays.asList(NonterminalToken.LOCAL_VARIABLE_DECLARATION_STATEMENT),
        Arrays.asList(NonterminalToken.STATEMENT)
      )
    );
    productions.put(
      NonterminalToken.LOCAL_VARIABLE_DECLARATION_STATEMENT,
      Arrays.asList(
        Arrays.asList(NonterminalToken.LOCAL_VARIABLE_DECLARATION, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.LOCAL_VARIABLE_DECLARATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.TYPE, NonterminalToken.VARIABLE_DECLARATORS)
      )
    );
    productions.put(
      NonterminalToken.STATEMENT,
      Arrays.asList(
        Arrays.asList(NonterminalToken.STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT),
        Arrays.asList(NonterminalToken.LABELED_STATEMENT),
        Arrays.asList(NonterminalToken.IF_THEN_STATEMENT),
        Arrays.asList(NonterminalToken.IF_THEN_ELSE_STATEMENT),
        Arrays.asList(NonterminalToken.WHILE_STATEMENT),
        Arrays.asList(NonterminalToken.FOR_STATEMENT)
      )
    );
    productions.put(
      NonterminalToken.STATEMENT_NO_SHORT_IF,
      Arrays.asList(
        Arrays.asList(NonterminalToken.STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT),
        Arrays.asList(NonterminalToken.LABELED_STATEMENT_NO_SHORT_IF),
        Arrays.asList(NonterminalToken.IF_THEN_ELSE_STATEMENT_NO_SHORT_IF),
        Arrays.asList(NonterminalToken.WHILE_STATEMENT_NO_SHORT_IF),
        Arrays.asList(NonterminalToken.FOR_STATEMENT_NO_SHORT_IF)
      )
    );
    productions.put(
      NonterminalToken.STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT,
      Arrays.asList(
        Arrays.asList(NonterminalToken.BLOCK),
        Arrays.asList(NonterminalToken.EMPTY_STATEMENT),
        Arrays.asList(NonterminalToken.EXPRESSION_STATEMENT),
        Arrays.asList(NonterminalToken.SWITCH_STATEMENT),
        Arrays.asList(NonterminalToken.DO_STATEMENT),
        Arrays.asList(NonterminalToken.BREAK_STATEMENT),
        Arrays.asList(NonterminalToken.CONTINUE_STATEMENT),
        Arrays.asList(NonterminalToken.RETURN_STATEMENT),
        Arrays.asList(NonterminalToken.SYNCHRONIZED_STATEMENT),
        Arrays.asList(NonterminalToken.THROW_STATEMENT),
        Arrays.asList(NonterminalToken.TRY_STATEMENT)
      )
    );
    productions.put(
      NonterminalToken.EMPTY_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.LABELED_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.IDENTIFIER, TerminalToken.COLON, NonterminalToken.STATEMENT)
      )
    );
    productions.put(
      NonterminalToken.LABELED_STATEMENT_NO_SHORT_IF,
      Arrays.asList(
        Arrays.asList(TerminalToken.IDENTIFIER, TerminalToken.COLON, NonterminalToken.STATEMENT_NO_SHORT_IF)
      )
    );
    productions.put(
      NonterminalToken.EXPRESSION_STATEMENT,
      Arrays.asList(
        Arrays.asList(NonterminalToken.STATEMENT_EXPRESSION, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.STATEMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.ASSIGNMENT),
        Arrays.asList(NonterminalToken.PRE_INCREMENT_EXPRESSION),
        Arrays.asList(NonterminalToken.PRE_DECREMENT_EXPRESSION),
        Arrays.asList(NonterminalToken.POST_INCREMENT_EXPRESSION),
        Arrays.asList(NonterminalToken.POST_DECREMENT_EXPRESSION),
        Arrays.asList(NonterminalToken.METHOD_INVOCATION),
        Arrays.asList(NonterminalToken.CLASS_INSTANCE_CREATION_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.IF_THEN_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.IF, TerminalToken.OPEN_BR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_BR, NonterminalToken.STATEMENT)
      )
    );
    productions.put(
      NonterminalToken.IF_THEN_ELSE_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.IF, TerminalToken.OPEN_BR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_BR, NonterminalToken.STATEMENT_NO_SHORT_IF, TerminalToken.ELSE, NonterminalToken.STATEMENT)
      )
    );
    productions.put(
      NonterminalToken.IF_THEN_ELSE_STATEMENT_NO_SHORT_IF,
      Arrays.asList(
        Arrays.asList(TerminalToken.IF, TerminalToken.OPEN_BR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_BR, NonterminalToken.STATEMENT_NO_SHORT_IF, TerminalToken.ELSE, NonterminalToken.STATEMENT_NO_SHORT_IF)
      )
    );
    productions.put(
      NonterminalToken.SWITCH_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.SWITCH, TerminalToken.OPEN_BR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_BR, NonterminalToken.SWITCH_BLOCK)
      )
    );
    productions.put(
      NonterminalToken.SWITCH_BLOCK_STATEMENT_GROUPS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.SWITCH_BLOCK_STATEMENT_GROUPS)
      )
    );
    productions.put(
      NonterminalToken.SWITCH_LABELS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.SWITCH_LABELS)
      )
    );
    productions.put(
      NonterminalToken.SWITCH_BLOCK,
      Arrays.asList(
        Arrays.asList(TerminalToken.OPEN_CBR, NonterminalToken.SWITCH_BLOCK_STATEMENT_GROUPS_OPT, NonterminalToken.SWITCH_LABELS_OPT, TerminalToken.CLOSE_CBR)
      )
    );
    productions.put(
      NonterminalToken.SWITCH_BLOCK_STATEMENT_GROUPS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.SWITCH_BLOCK_STATEMENT_GROUP),
        Arrays.asList(NonterminalToken.SWITCH_BLOCK_STATEMENT_GROUPS, NonterminalToken.SWITCH_BLOCK_STATEMENT_GROUP)
      )
    );
    productions.put(
      NonterminalToken.SWITCH_BLOCK_STATEMENT_GROUP,
      Arrays.asList(
        Arrays.asList(NonterminalToken.SWITCH_LABELS, NonterminalToken.BLOCK_STATEMENTS)
      )
    );
    productions.put(
      NonterminalToken.SWITCH_LABELS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.SWITCH_LABEL),
        Arrays.asList(NonterminalToken.SWITCH_LABELS, NonterminalToken.SWITCH_LABEL)
      )
    );
    productions.put(
      NonterminalToken.SWITCH_LABEL,
      Arrays.asList(
        Arrays.asList(TerminalToken.CASE, NonterminalToken.CONSTANT_EXPRESSION, TerminalToken.COLON),
        Arrays.asList(TerminalToken.DEFAULT, TerminalToken.COLON)
      )
    );
    productions.put(
      NonterminalToken.WHILE_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.WHILE, TerminalToken.OPEN_BR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_BR, NonterminalToken.STATEMENT)
      )
    );
    productions.put(
      NonterminalToken.WHILE_STATEMENT_NO_SHORT_IF,
      Arrays.asList(
        Arrays.asList(TerminalToken.WHILE, TerminalToken.OPEN_BR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_BR, NonterminalToken.STATEMENT_NO_SHORT_IF)
      )
    );
    productions.put(
      NonterminalToken.DO_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.DO, NonterminalToken.STATEMENT, TerminalToken.WHILE, TerminalToken.OPEN_BR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_BR, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.FOR_INIT_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.FOR_INIT)
      )
    );
    productions.put(
      NonterminalToken.EXPRESSION_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.FOR_UPDATE_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.FOR_UPDATE)
      )
    );
    productions.put(
      NonterminalToken.FOR_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.FOR, TerminalToken.OPEN_BR, NonterminalToken.FOR_INIT_OPT, TerminalToken.SEMICOLON, NonterminalToken.EXPRESSION_OPT, TerminalToken.SEMICOLON, NonterminalToken.FOR_UPDATE_OPT, TerminalToken.CLOSE_BR, NonterminalToken.STATEMENT)
      )
    );
    productions.put(
      NonterminalToken.FOR_STATEMENT_NO_SHORT_IF,
      Arrays.asList(
        Arrays.asList(TerminalToken.FOR, TerminalToken.OPEN_BR, NonterminalToken.FOR_INIT_OPT, TerminalToken.SEMICOLON, NonterminalToken.EXPRESSION_OPT, TerminalToken.SEMICOLON, NonterminalToken.FOR_UPDATE_OPT, TerminalToken.CLOSE_BR, NonterminalToken.STATEMENT_NO_SHORT_IF)
      )
    );
    productions.put(
      NonterminalToken.FOR_INIT,
      Arrays.asList(
        Arrays.asList(NonterminalToken.STATEMENT_EXPRESSION_LIST),
        Arrays.asList(NonterminalToken.LOCAL_VARIABLE_DECLARATION)
      )
    );
    productions.put(
      NonterminalToken.FOR_UPDATE,
      Arrays.asList(
        Arrays.asList(NonterminalToken.STATEMENT_EXPRESSION_LIST)
      )
    );
    productions.put(
      NonterminalToken.STATEMENT_EXPRESSION_LIST,
      Arrays.asList(
        Arrays.asList(NonterminalToken.STATEMENT_EXPRESSION),
        Arrays.asList(NonterminalToken.STATEMENT_EXPRESSION_LIST, TerminalToken.COMMA, NonterminalToken.STATEMENT_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.IDENTIFIER_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(TerminalToken.IDENTIFIER)
      )
    );
    productions.put(
      NonterminalToken.BREAK_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.BREAK, NonterminalToken.IDENTIFIER_OPT, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.CONTINUE_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.CONTINUE, NonterminalToken.IDENTIFIER_OPT, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.RETURN_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.RETURN, NonterminalToken.EXPRESSION_OPT, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.THROW_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.THROW, NonterminalToken.EXPRESSION, TerminalToken.SEMICOLON)
      )
    );
    productions.put(
      NonterminalToken.SYNCHRONIZED_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.SYNCHRONIZED, TerminalToken.OPEN_BR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_BR, NonterminalToken.BLOCK)
      )
    );
    productions.put(
      NonterminalToken.CATCHES_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.CATCHES)
      )
    );
    productions.put(
      NonterminalToken.TRY_STATEMENT,
      Arrays.asList(
        Arrays.asList(TerminalToken.TRY, NonterminalToken.BLOCK, NonterminalToken.CATCHES),
        Arrays.asList(TerminalToken.TRY, NonterminalToken.BLOCK, NonterminalToken.CATCHES_OPT, NonterminalToken.FINALLY)
      )
    );
    productions.put(
      NonterminalToken.CATCHES,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CATCH_CLAUSE),
        Arrays.asList(NonterminalToken.CATCHES, NonterminalToken.CATCH_CLAUSE)
      )
    );
    productions.put(
      NonterminalToken.CATCH_CLAUSE,
      Arrays.asList(
        Arrays.asList(TerminalToken.CATCH, TerminalToken.OPEN_BR, NonterminalToken.FORMAL_PARAMETER, TerminalToken.CLOSE_BR, NonterminalToken.BLOCK)
      )
    );
    productions.put(
      NonterminalToken.FINALLY,
      Arrays.asList(
        Arrays.asList(TerminalToken.FINALLY, NonterminalToken.BLOCK)
      )
    );
    productions.put(
      NonterminalToken.PRIMARY,
      Arrays.asList(
        Arrays.asList(NonterminalToken.PRIMARY_NO_NEW_ARRAY),
        Arrays.asList(NonterminalToken.ARRAY_CREATION_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.PRIMARY_NO_NEW_ARRAY,
      Arrays.asList(
        Arrays.asList(NonterminalToken.LITERAL),
        Arrays.asList(TerminalToken.THIS),
        Arrays.asList(TerminalToken.OPEN_BR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_BR),
        Arrays.asList(NonterminalToken.CLASS_INSTANCE_CREATION_EXPRESSION),
        Arrays.asList(NonterminalToken.FIELD_ACCESS),
        Arrays.asList(NonterminalToken.METHOD_INVOCATION),
        Arrays.asList(NonterminalToken.ARRAY_ACCESS)
      )
    );
    productions.put(
      NonterminalToken.CLASS_INSTANCE_CREATION_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TerminalToken.NEW, NonterminalToken.CLASS_TYPE, TerminalToken.OPEN_BR, NonterminalToken.ARGUMENT_LIST_OPT, TerminalToken.CLOSE_BR)
      )
    );
    productions.put(
      NonterminalToken.ARGUMENT_LIST,
      Arrays.asList(
        Arrays.asList(NonterminalToken.EXPRESSION),
        Arrays.asList(NonterminalToken.ARGUMENT_LIST, TerminalToken.COMMA, NonterminalToken.EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.DIMS_OPT,
      Arrays.asList(
        Arrays.asList(),
        Arrays.asList(NonterminalToken.DIMS)
      )
    );
    productions.put(
      NonterminalToken.ARRAY_CREATION_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TerminalToken.NEW, NonterminalToken.PRIMITIVE_TYPE, NonterminalToken.DIM_EXPRS, NonterminalToken.DIMS_OPT),
        Arrays.asList(TerminalToken.NEW, NonterminalToken.CLASS_OR_INTERFACE_TYPE, NonterminalToken.DIM_EXPRS, NonterminalToken.DIMS_OPT)
      )
    );
    productions.put(
      NonterminalToken.DIM_EXPRS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.DIM_EXPR),
        Arrays.asList(NonterminalToken.DIM_EXPRS, NonterminalToken.DIM_EXPR)
      )
    );
    productions.put(
      NonterminalToken.DIM_EXPR,
      Arrays.asList(
        Arrays.asList(TerminalToken.OPEN_SBR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_SBR)
      )
    );
    productions.put(
      NonterminalToken.DIMS,
      Arrays.asList(
        Arrays.asList(TerminalToken.OPEN_SBR, TerminalToken.CLOSE_SBR),
        Arrays.asList(NonterminalToken.DIMS, TerminalToken.OPEN_SBR, TerminalToken.CLOSE_SBR)
      )
    );
    productions.put(
      NonterminalToken.FIELD_ACCESS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.PRIMARY, TerminalToken.DOT, TerminalToken.IDENTIFIER),
        Arrays.asList(TerminalToken.SUPER, TerminalToken.DOT, TerminalToken.IDENTIFIER)
      )
    );
    productions.put(
      NonterminalToken.METHOD_INVOCATION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.NAME, TerminalToken.OPEN_BR, NonterminalToken.ARGUMENT_LIST_OPT, TerminalToken.CLOSE_BR),
        Arrays.asList(NonterminalToken.PRIMARY, TerminalToken.DOT, TerminalToken.IDENTIFIER, TerminalToken.OPEN_BR, NonterminalToken.ARGUMENT_LIST_OPT, TerminalToken.CLOSE_BR),
        Arrays.asList(TerminalToken.SUPER, TerminalToken.DOT, TerminalToken.IDENTIFIER, TerminalToken.OPEN_BR, NonterminalToken.ARGUMENT_LIST_OPT, TerminalToken.CLOSE_BR)
      )
    );
    productions.put(
      NonterminalToken.ARRAY_ACCESS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.NAME, TerminalToken.OPEN_SBR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_SBR),
        Arrays.asList(NonterminalToken.PRIMARY_NO_NEW_ARRAY, TerminalToken.OPEN_SBR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_SBR)
      )
    );
    productions.put(
      NonterminalToken.POSTFIX_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.PRIMARY),
        Arrays.asList(NonterminalToken.NAME),
        Arrays.asList(NonterminalToken.POST_INCREMENT_EXPRESSION),
        Arrays.asList(NonterminalToken.POST_DECREMENT_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.POST_INCREMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.POSTFIX_EXPRESSION, TerminalToken.ASSIGN_INCREMENT)
      )
    );
    productions.put(
      NonterminalToken.POST_DECREMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.POSTFIX_EXPRESSION, TerminalToken.ASSIGN_DECREMENT)
      )
    );
    productions.put(
      NonterminalToken.UNARY_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.PRE_INCREMENT_EXPRESSION),
        Arrays.asList(NonterminalToken.PRE_DECREMENT_EXPRESSION),
        Arrays.asList(TerminalToken.OP_PLUS, NonterminalToken.UNARY_EXPRESSION),
        Arrays.asList(TerminalToken.OP_MINUS, NonterminalToken.UNARY_EXPRESSION),
        Arrays.asList(NonterminalToken.UNARY_EXPRESSION_NOT_PLUS_MINUS)
      )
    );
    productions.put(
      NonterminalToken.PRE_INCREMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TerminalToken.ASSIGN_INCREMENT, NonterminalToken.UNARY_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.PRE_DECREMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TerminalToken.ASSIGN_DECREMENT, NonterminalToken.UNARY_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.UNARY_EXPRESSION_NOT_PLUS_MINUS,
      Arrays.asList(
        Arrays.asList(NonterminalToken.POSTFIX_EXPRESSION),
        Arrays.asList(TerminalToken.BITWISE_NOT, NonterminalToken.UNARY_EXPRESSION),
        Arrays.asList(TerminalToken.BOOL_OP_NOT, NonterminalToken.UNARY_EXPRESSION),
        Arrays.asList(NonterminalToken.CAST_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.CAST_EXPRESSION,
      Arrays.asList(
        Arrays.asList(TerminalToken.OPEN_BR, NonterminalToken.PRIMITIVE_TYPE, NonterminalToken.DIMS_OPT, TerminalToken.CLOSE_BR, NonterminalToken.UNARY_EXPRESSION),
        Arrays.asList(TerminalToken.OPEN_BR, NonterminalToken.EXPRESSION, TerminalToken.CLOSE_BR, NonterminalToken.UNARY_EXPRESSION_NOT_PLUS_MINUS),
        Arrays.asList(TerminalToken.OPEN_BR, NonterminalToken.NAME, NonterminalToken.DIMS, TerminalToken.CLOSE_BR, NonterminalToken.UNARY_EXPRESSION_NOT_PLUS_MINUS)
      )
    );
    productions.put(
      NonterminalToken.MULTIPLICATIVE_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.UNARY_EXPRESSION),
        Arrays.asList(NonterminalToken.MULTIPLICATIVE_EXPRESSION, TerminalToken.STAR, NonterminalToken.UNARY_EXPRESSION),
        Arrays.asList(NonterminalToken.MULTIPLICATIVE_EXPRESSION, TerminalToken.OP_DIV, NonterminalToken.UNARY_EXPRESSION),
        Arrays.asList(NonterminalToken.MULTIPLICATIVE_EXPRESSION, TerminalToken.OP_REMAINDER, NonterminalToken.UNARY_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.ADDITIVE_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.MULTIPLICATIVE_EXPRESSION),
        Arrays.asList(NonterminalToken.ADDITIVE_EXPRESSION, TerminalToken.OP_PLUS, NonterminalToken.MULTIPLICATIVE_EXPRESSION),
        Arrays.asList(NonterminalToken.ADDITIVE_EXPRESSION, TerminalToken.OP_MINUS, NonterminalToken.MULTIPLICATIVE_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.SHIFT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.ADDITIVE_EXPRESSION),
        Arrays.asList(NonterminalToken.SHIFT_EXPRESSION, TerminalToken.OP_LEFT_SHIFT, NonterminalToken.ADDITIVE_EXPRESSION),
        Arrays.asList(NonterminalToken.SHIFT_EXPRESSION, TerminalToken.OP_RIGHT_SHIFT, NonterminalToken.ADDITIVE_EXPRESSION),
        Arrays.asList(NonterminalToken.SHIFT_EXPRESSION, TerminalToken.OP_UNSIGNED_RIGHT_SHIFT, NonterminalToken.ADDITIVE_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.RELATIONAL_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.SHIFT_EXPRESSION),
        Arrays.asList(NonterminalToken.RELATIONAL_EXPRESSION, TerminalToken.COMP_LESS_THAN, NonterminalToken.SHIFT_EXPRESSION),
        Arrays.asList(NonterminalToken.RELATIONAL_EXPRESSION, TerminalToken.COMP_GREATER_THAN, NonterminalToken.SHIFT_EXPRESSION),
        Arrays.asList(NonterminalToken.RELATIONAL_EXPRESSION, TerminalToken.COMP_LESS_THAN_EQ, NonterminalToken.SHIFT_EXPRESSION),
        Arrays.asList(NonterminalToken.RELATIONAL_EXPRESSION, TerminalToken.COMP_GREATER_THAN_EQ, NonterminalToken.SHIFT_EXPRESSION),
        Arrays.asList(NonterminalToken.RELATIONAL_EXPRESSION, TerminalToken.INSTANCEOF, NonterminalToken.REFERENCE_TYPE)
      )
    );
    productions.put(
      NonterminalToken.EQUALITY_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.RELATIONAL_EXPRESSION),
        Arrays.asList(NonterminalToken.EQUALITY_EXPRESSION, TerminalToken.COMP_EQ, NonterminalToken.RELATIONAL_EXPRESSION),
        Arrays.asList(NonterminalToken.EQUALITY_EXPRESSION, TerminalToken.COMP_NOT_EQ, NonterminalToken.RELATIONAL_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.AND_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.EQUALITY_EXPRESSION),
        Arrays.asList(NonterminalToken.AND_EXPRESSION, TerminalToken.BITWISE_AND, NonterminalToken.EQUALITY_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.EXCLUSIVE_OR_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.AND_EXPRESSION),
        Arrays.asList(NonterminalToken.EXCLUSIVE_OR_EXPRESSION, TerminalToken.BITWISE_XOR, NonterminalToken.AND_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.INCLUSIVE_OR_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.EXCLUSIVE_OR_EXPRESSION),
        Arrays.asList(NonterminalToken.INCLUSIVE_OR_EXPRESSION, TerminalToken.BITWISE_OR, NonterminalToken.EXCLUSIVE_OR_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.CONDITIONAL_AND_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.INCLUSIVE_OR_EXPRESSION),
        Arrays.asList(NonterminalToken.CONDITIONAL_AND_EXPRESSION, TerminalToken.BOOL_OP_AND, NonterminalToken.INCLUSIVE_OR_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.CONDITIONAL_OR_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CONDITIONAL_AND_EXPRESSION),
        Arrays.asList(NonterminalToken.CONDITIONAL_OR_EXPRESSION, TerminalToken.BOOL_OP_OR, NonterminalToken.CONDITIONAL_AND_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.CONDITIONAL_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CONDITIONAL_OR_EXPRESSION),
        Arrays.asList(NonterminalToken.CONDITIONAL_OR_EXPRESSION, TerminalToken.QUESTION_MARK, NonterminalToken.EXPRESSION, TerminalToken.COLON, NonterminalToken.CONDITIONAL_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.ASSIGNMENT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.CONDITIONAL_EXPRESSION),
        Arrays.asList(NonterminalToken.ASSIGNMENT)
      )
    );
    productions.put(
      NonterminalToken.ASSIGNMENT,
      Arrays.asList(
        Arrays.asList(NonterminalToken.LEFT_HAND_SIDE, NonterminalToken.ASSIGNMENT_OPERATOR, NonterminalToken.ASSIGNMENT_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.LEFT_HAND_SIDE,
      Arrays.asList(
        Arrays.asList(NonterminalToken.NAME),
        Arrays.asList(NonterminalToken.FIELD_ACCESS),
        Arrays.asList(NonterminalToken.ARRAY_ACCESS)
      )
    );
    productions.put(
      NonterminalToken.ASSIGNMENT_OPERATOR,
      Arrays.asList(
        Arrays.asList(TerminalToken.ASSIGN)
      )
    );
    productions.put(
      NonterminalToken.EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.ASSIGNMENT_EXPRESSION)
      )
    );
    productions.put(
      NonterminalToken.CONSTANT_EXPRESSION,
      Arrays.asList(
        Arrays.asList(NonterminalToken.EXPRESSION)
      )
    );
    HashSet<TerminalToken> terminals = new HashSet(Arrays.asList(TerminalToken.values()));
    HashSet<NonterminalToken> nonterminals = new HashSet(Arrays.asList(NonterminalToken.values()));
    grammar = new Grammar(
      terminals,
      nonterminals,
      productions,
      NonterminalToken.GOAL
    );
  }
}
