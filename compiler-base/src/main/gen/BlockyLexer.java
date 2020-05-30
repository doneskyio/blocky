// Generated from /Users/brock/git/donesky/blocky/compiler-base/src/main/antlr/BlockyLexer.g4 by ANTLR 4.8
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BlockyLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BLOCK_COMMENT=1, BLOCK_ESCAPE=2, SEA_WS=3, BLOCK_OPEN=4, TEXT=5, BLOCK_CLOSE=6, 
		BLOCK_ELSE=7, BLOCK_CTX_SET=8, BLOCK_CTX=9, BLOCK_REF=10, BLOCK_NAME=11, 
		BLOCK_ATTRIBUTE_NAME=12, BLOCK_WHITESPACE=13, BLOCK_SLASH_CLOSE=14, BLOCK_SLASH=15, 
		BLOCK_EQUALS=16, BLOCK_EXPRESSION_OPEN=17, ATTVALUE_VALUE=18, ATTRIBUTE=19, 
		BLOCK_EXPRESSION_CLOSE=20, EXPRESSION_WHITESPACE=21, EXPRESION_GROUP_OPEN=22, 
		EXPRESION_GROUP_CLOSE=23, EXPRESSION_NEGATIVE=24, EXPRESSION_LOGIC=25, 
		EXPRESSION_OPERATOR=26, EXPRESSION_DOT=27, EXPRESSION_NULL=28, EXPRESSION_BOOLEAN=29, 
		EXPRESSION_NUMBER=30, EXPRESSION_STRING=31, EXPRESSION_VARIABLE_NAME=32;
	public static final int
		BLOCK=1, ATTVALUE=2, EXPRESSION=3;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "BLOCK", "ATTVALUE", "EXPRESSION"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"BLOCK_COMMENT", "BLOCK_ESCAPE", "SEA_WS", "BLOCK_OPEN", "TEXT", "BLOCK_CLOSE", 
			"BLOCK_ELSE_NAME", "BLOCK_ELSE", "BLOCK_CTX_SET_NAME", "BLOCK_CTX_SET", 
			"BLOCK_CTX_NAME", "BLOCK_CTX", "BLOCK_REF_NAME", "BLOCK_REF", "BLOCK_NAME", 
			"BLOCK_ATTRIBUTE_NAME", "BLOCK_WHITESPACE", "BLOCK_SLASH_CLOSE", "BLOCK_SLASH", 
			"BLOCK_EQUALS", "BLOCK_EXPRESSION_OPEN", "DIGIT", "ATTVALUE_VALUE", "ATTRIBUTE", 
			"DOUBLE_QUOTE_STRING", "BLOCK_EXPRESSION_CLOSE", "EXPRESSION_WHITESPACE", 
			"EXPRESION_GROUP_OPEN", "EXPRESION_GROUP_CLOSE", "EXPRESSION_NEGATIVE", 
			"EXPRESSION_LOGIC", "EXPRESSION_OPERATOR", "EXPRESSION_DOT", "EXPRESSION_NULL", 
			"EXPRESSION_BOOLEAN", "EXPRESSION_NUMBER", "EXPRESSION_STRING", "EXPRESSION_VARIABLE_NAME", 
			"EXPONENT", "INTEGER"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "'/]'", "'/'", "'='", null, null, null, null, null, "'('", 
			"')'", "'!'", null, null, "'.'", "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BLOCK_COMMENT", "BLOCK_ESCAPE", "SEA_WS", "BLOCK_OPEN", "TEXT", 
			"BLOCK_CLOSE", "BLOCK_ELSE", "BLOCK_CTX_SET", "BLOCK_CTX", "BLOCK_REF", 
			"BLOCK_NAME", "BLOCK_ATTRIBUTE_NAME", "BLOCK_WHITESPACE", "BLOCK_SLASH_CLOSE", 
			"BLOCK_SLASH", "BLOCK_EQUALS", "BLOCK_EXPRESSION_OPEN", "ATTVALUE_VALUE", 
			"ATTRIBUTE", "BLOCK_EXPRESSION_CLOSE", "EXPRESSION_WHITESPACE", "EXPRESION_GROUP_OPEN", 
			"EXPRESION_GROUP_CLOSE", "EXPRESSION_NEGATIVE", "EXPRESSION_LOGIC", "EXPRESSION_OPERATOR", 
			"EXPRESSION_DOT", "EXPRESSION_NULL", "EXPRESSION_BOOLEAN", "EXPRESSION_NUMBER", 
			"EXPRESSION_STRING", "EXPRESSION_VARIABLE_NAME"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public BlockyLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BlockyLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\"\u01b3\b\1\b\1\b"+
		"\1\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t"+
		"\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21"+
		"\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30"+
		"\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37"+
		"\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\7\2]\n\2\f\2\16\2`\13\2\3\2\3\2\3\2\3\2\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\7\3l\n\3\f\3\16\3o\13\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4"+
		"\5\4x\n\4\3\4\6\4{\n\4\r\4\16\4|\3\5\3\5\3\5\3\5\3\6\6\6\u0084\n\6\r\6"+
		"\16\6\u0085\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5"+
		"\b\u0096\n\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\5\16\u00c8\n\16\3\17\3\17\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\5\20\u00ed\n\20\3\21\6\21\u00f0\n\21\r\21\16\21\u00f1\3\21\7\21"+
		"\u00f5\n\21\f\21\16\21\u00f8\13\21\3\21\3\21\7\21\u00fc\n\21\f\21\16\21"+
		"\u00ff\13\21\7\21\u0101\n\21\f\21\16\21\u0104\13\21\3\22\3\22\3\22\3\22"+
		"\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26"+
		"\3\26\3\27\3\27\3\30\7\30\u011c\n\30\f\30\16\30\u011f\13\30\3\30\3\30"+
		"\3\30\3\30\3\31\3\31\3\32\3\32\7\32\u0129\n\32\f\32\16\32\u012c\13\32"+
		"\3\32\3\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\36\3\36"+
		"\3\37\3\37\3 \3 \3 \3 \5 \u0142\n \3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3"+
		"!\3!\3!\3!\5!\u0153\n!\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\5$\u0165\n$\3%\3%\3%\7%\u016a\n%\f%\16%\u016d\13%\3%\5%\u0170\n%\3"+
		"%\3%\6%\u0174\n%\r%\16%\u0175\3%\5%\u0179\n%\3%\3%\5%\u017d\n%\5%\u017f"+
		"\n%\3&\3&\7&\u0183\n&\f&\16&\u0186\13&\3&\3&\3\'\6\'\u018b\n\'\r\'\16"+
		"\'\u018c\3\'\7\'\u0190\n\'\f\'\16\'\u0193\13\'\3\'\3\'\7\'\u0197\n\'\f"+
		"\'\16\'\u019a\13\'\7\'\u019c\n\'\f\'\16\'\u019f\13\'\3(\3(\5(\u01a3\n"+
		"(\3(\6(\u01a6\n(\r(\16(\u01a7\3)\3)\3)\7)\u01ad\n)\f)\16)\u01b0\13)\5"+
		")\u01b2\n)\4^m\2*\6\3\b\4\n\5\f\6\16\7\20\b\22\2\24\t\26\2\30\n\32\2\34"+
		"\13\36\2 \f\"\r$\16&\17(\20*\21,\22.\23\60\2\62\24\64\25\66\28\26:\27"+
		"<\30>\31@\32B\33D\34F\35H\36J\37L N!P\"R\2T\2\6\2\3\4\5\17\4\2\13\13\""+
		"\"\3\2]]\4\2C\\c|\5\2\62;C\\c|\5\2\13\f\17\17\"\"\3\2\62;\3\2\"\"\4\2"+
		"$$>>\5\2,-//\61\61\4\2>>@@\4\2GGgg\4\2--//\3\2\63;\2\u01d3\2\6\3\2\2\2"+
		"\2\b\3\2\2\2\2\n\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2\3\20\3\2\2\2\3\24\3"+
		"\2\2\2\3\30\3\2\2\2\3\34\3\2\2\2\3 \3\2\2\2\3\"\3\2\2\2\3$\3\2\2\2\3&"+
		"\3\2\2\2\3(\3\2\2\2\3*\3\2\2\2\3,\3\2\2\2\3.\3\2\2\2\4\62\3\2\2\2\4\64"+
		"\3\2\2\2\58\3\2\2\2\5:\3\2\2\2\5<\3\2\2\2\5>\3\2\2\2\5@\3\2\2\2\5B\3\2"+
		"\2\2\5D\3\2\2\2\5F\3\2\2\2\5H\3\2\2\2\5J\3\2\2\2\5L\3\2\2\2\5N\3\2\2\2"+
		"\5P\3\2\2\2\6V\3\2\2\2\be\3\2\2\2\nz\3\2\2\2\f~\3\2\2\2\16\u0083\3\2\2"+
		"\2\20\u0087\3\2\2\2\22\u0095\3\2\2\2\24\u0097\3\2\2\2\26\u0099\3\2\2\2"+
		"\30\u00a1\3\2\2\2\32\u00a3\3\2\2\2\34\u00aa\3\2\2\2\36\u00c7\3\2\2\2 "+
		"\u00c9\3\2\2\2\"\u00ec\3\2\2\2$\u00ef\3\2\2\2&\u0105\3\2\2\2(\u0109\3"+
		"\2\2\2*\u010e\3\2\2\2,\u0110\3\2\2\2.\u0114\3\2\2\2\60\u0118\3\2\2\2\62"+
		"\u011d\3\2\2\2\64\u0124\3\2\2\2\66\u0126\3\2\2\28\u012f\3\2\2\2:\u0133"+
		"\3\2\2\2<\u0137\3\2\2\2>\u0139\3\2\2\2@\u013b\3\2\2\2B\u0141\3\2\2\2D"+
		"\u0152\3\2\2\2F\u0154\3\2\2\2H\u0156\3\2\2\2J\u0164\3\2\2\2L\u017e\3\2"+
		"\2\2N\u0180\3\2\2\2P\u018a\3\2\2\2R\u01a0\3\2\2\2T\u01b1\3\2\2\2VW\7]"+
		"\2\2WX\7#\2\2XY\7/\2\2YZ\7/\2\2Z^\3\2\2\2[]\13\2\2\2\\[\3\2\2\2]`\3\2"+
		"\2\2^_\3\2\2\2^\\\3\2\2\2_a\3\2\2\2`^\3\2\2\2ab\7/\2\2bc\7/\2\2cd\7_\2"+
		"\2d\7\3\2\2\2ef\7]\2\2fg\7]\2\2gh\7/\2\2hi\7/\2\2im\3\2\2\2jl\13\2\2\2"+
		"kj\3\2\2\2lo\3\2\2\2mn\3\2\2\2mk\3\2\2\2np\3\2\2\2om\3\2\2\2pq\7/\2\2"+
		"qr\7/\2\2rs\7_\2\2st\7_\2\2t\t\3\2\2\2u{\t\2\2\2vx\7\17\2\2wv\3\2\2\2"+
		"wx\3\2\2\2xy\3\2\2\2y{\7\f\2\2zu\3\2\2\2zw\3\2\2\2{|\3\2\2\2|z\3\2\2\2"+
		"|}\3\2\2\2}\13\3\2\2\2~\177\7]\2\2\177\u0080\3\2\2\2\u0080\u0081\b\5\2"+
		"\2\u0081\r\3\2\2\2\u0082\u0084\n\3\2\2\u0083\u0082\3\2\2\2\u0084\u0085"+
		"\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086\17\3\2\2\2\u0087"+
		"\u0088\7_\2\2\u0088\u0089\3\2\2\2\u0089\u008a\b\7\3\2\u008a\21\3\2\2\2"+
		"\u008b\u008c\7g\2\2\u008c\u008d\7n\2\2\u008d\u008e\7u\2\2\u008e\u008f"+
		"\7g\2\2\u008f\u0090\7k\2\2\u0090\u0096\7h\2\2\u0091\u0092\7g\2\2\u0092"+
		"\u0093\7n\2\2\u0093\u0094\7u\2\2\u0094\u0096\7g\2\2\u0095\u008b\3\2\2"+
		"\2\u0095\u0091\3\2\2\2\u0096\23\3\2\2\2\u0097\u0098\5\22\b\2\u0098\25"+
		"\3\2\2\2\u0099\u009a\7e\2\2\u009a\u009b\7v\2\2\u009b\u009c\7z\2\2\u009c"+
		"\u009d\7<\2\2\u009d\u009e\7u\2\2\u009e\u009f\7g\2\2\u009f\u00a0\7v\2\2"+
		"\u00a0\27\3\2\2\2\u00a1\u00a2\5\26\n\2\u00a2\31\3\2\2\2\u00a3\u00a4\7"+
		"e\2\2\u00a4\u00a5\7v\2\2\u00a5\u00a6\7z\2\2\u00a6\u00a7\7<\2\2\u00a7\u00a8"+
		"\3\2\2\2\u00a8\u00a9\5$\21\2\u00a9\33\3\2\2\2\u00aa\u00ab\5\32\f\2\u00ab"+
		"\35\3\2\2\2\u00ac\u00ad\7t\2\2\u00ad\u00ae\7g\2\2\u00ae\u00af\7h\2\2\u00af"+
		"\u00b0\7<\2\2\u00b0\u00b1\7v\2\2\u00b1\u00b2\7g\2\2\u00b2\u00b3\7o\2\2"+
		"\u00b3\u00b4\7r\2\2\u00b4\u00b5\7n\2\2\u00b5\u00b6\7c\2\2\u00b6\u00b7"+
		"\7v\2\2\u00b7\u00c8\7g\2\2\u00b8\u00b9\7t\2\2\u00b9\u00ba\7g\2\2\u00ba"+
		"\u00bb\7h\2\2\u00bb\u00bc\7<\2\2\u00bc\u00bd\7r\2\2\u00bd\u00be\7n\2\2"+
		"\u00be\u00bf\7c\2\2\u00bf\u00c0\7e\2\2\u00c0\u00c1\7g\2\2\u00c1\u00c2"+
		"\7j\2\2\u00c2\u00c3\7q\2\2\u00c3\u00c4\7n\2\2\u00c4\u00c5\7f\2\2\u00c5"+
		"\u00c6\7g\2\2\u00c6\u00c8\7t\2\2\u00c7\u00ac\3\2\2\2\u00c7\u00b8\3\2\2"+
		"\2\u00c8\37\3\2\2\2\u00c9\u00ca\5\36\16\2\u00ca!\3\2\2\2\u00cb\u00cc\7"+
		"v\2\2\u00cc\u00cd\7g\2\2\u00cd\u00ce\7o\2\2\u00ce\u00cf\7r\2\2\u00cf\u00d0"+
		"\7n\2\2\u00d0\u00d1\7c\2\2\u00d1\u00d2\7v\2\2\u00d2\u00ed\7g\2\2\u00d3"+
		"\u00d4\7r\2\2\u00d4\u00d5\7n\2\2\u00d5\u00d6\7c\2\2\u00d6\u00d7\7e\2\2"+
		"\u00d7\u00d8\7g\2\2\u00d8\u00d9\7j\2\2\u00d9\u00da\7q\2\2\u00da\u00db"+
		"\7n\2\2\u00db\u00dc\7f\2\2\u00dc\u00dd\7g\2\2\u00dd\u00ed\7t\2\2\u00de"+
		"\u00df\7k\2\2\u00df\u00ed\7h\2\2\u00e0\u00e1\7h\2\2\u00e1\u00e2\7q\2\2"+
		"\u00e2\u00ed\7t\2\2\u00e3\u00e4\7h\2\2\u00e4\u00e5\7q\2\2\u00e5\u00e6"+
		"\7t\2\2\u00e6\u00e7\7<\2\2\u00e7\u00e8\7k\2\2\u00e8\u00e9\7p\2\2\u00e9"+
		"\u00ea\7f\2\2\u00ea\u00eb\7g\2\2\u00eb\u00ed\7z\2\2\u00ec\u00cb\3\2\2"+
		"\2\u00ec\u00d3\3\2\2\2\u00ec\u00de\3\2\2\2\u00ec\u00e0\3\2\2\2\u00ec\u00e3"+
		"\3\2\2\2\u00ed#\3\2\2\2\u00ee\u00f0\t\4\2\2\u00ef\u00ee\3\2\2\2\u00f0"+
		"\u00f1\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f6\3\2"+
		"\2\2\u00f3\u00f5\t\5\2\2\u00f4\u00f3\3\2\2\2\u00f5\u00f8\3\2\2\2\u00f6"+
		"\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u0102\3\2\2\2\u00f8\u00f6\3\2"+
		"\2\2\u00f9\u00fd\7\60\2\2\u00fa\u00fc\t\5\2\2\u00fb\u00fa\3\2\2\2\u00fc"+
		"\u00ff\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u0101\3\2"+
		"\2\2\u00ff\u00fd\3\2\2\2\u0100\u00f9\3\2\2\2\u0101\u0104\3\2\2\2\u0102"+
		"\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103%\3\2\2\2\u0104\u0102\3\2\2\2"+
		"\u0105\u0106\t\6\2\2\u0106\u0107\3\2\2\2\u0107\u0108\b\22\4\2\u0108\'"+
		"\3\2\2\2\u0109\u010a\7\61\2\2\u010a\u010b\7_\2\2\u010b\u010c\3\2\2\2\u010c"+
		"\u010d\b\23\3\2\u010d)\3\2\2\2\u010e\u010f\7\61\2\2\u010f+\3\2\2\2\u0110"+
		"\u0111\7?\2\2\u0111\u0112\3\2\2\2\u0112\u0113\b\25\5\2\u0113-\3\2\2\2"+
		"\u0114\u0115\7]\2\2\u0115\u0116\3\2\2\2\u0116\u0117\b\26\6\2\u0117/\3"+
		"\2\2\2\u0118\u0119\t\7\2\2\u0119\61\3\2\2\2\u011a\u011c\t\b\2\2\u011b"+
		"\u011a\3\2\2\2\u011c\u011f\3\2\2\2\u011d\u011b\3\2\2\2\u011d\u011e\3\2"+
		"\2\2\u011e\u0120\3\2\2\2\u011f\u011d\3\2\2\2\u0120\u0121\5\64\31\2\u0121"+
		"\u0122\3\2\2\2\u0122\u0123\b\30\3\2\u0123\63\3\2\2\2\u0124\u0125\5\66"+
		"\32\2\u0125\65\3\2\2\2\u0126\u012a\7$\2\2\u0127\u0129\n\t\2\2\u0128\u0127"+
		"\3\2\2\2\u0129\u012c\3\2\2\2\u012a\u0128\3\2\2\2\u012a\u012b\3\2\2\2\u012b"+
		"\u012d\3\2\2\2\u012c\u012a\3\2\2\2\u012d\u012e\7$\2\2\u012e\67\3\2\2\2"+
		"\u012f\u0130\7_\2\2\u0130\u0131\3\2\2\2\u0131\u0132\b\33\3\2\u01329\3"+
		"\2\2\2\u0133\u0134\t\6\2\2\u0134\u0135\3\2\2\2\u0135\u0136\b\34\4\2\u0136"+
		";\3\2\2\2\u0137\u0138\7*\2\2\u0138=\3\2\2\2\u0139\u013a\7+\2\2\u013a?"+
		"\3\2\2\2\u013b\u013c\7#\2\2\u013cA\3\2\2\2\u013d\u013e\7(\2\2\u013e\u0142"+
		"\7(\2\2\u013f\u0140\7~\2\2\u0140\u0142\7~\2\2\u0141\u013d\3\2\2\2\u0141"+
		"\u013f\3\2\2\2\u0142C\3\2\2\2\u0143\u0144\7-\2\2\u0144\u0153\7-\2\2\u0145"+
		"\u0146\7/\2\2\u0146\u0153\7/\2\2\u0147\u0153\t\n\2\2\u0148\u0153\5@\37"+
		"\2\u0149\u0153\t\13\2\2\u014a\u014b\7>\2\2\u014b\u0153\7?\2\2\u014c\u014d"+
		"\7@\2\2\u014d\u0153\7?\2\2\u014e\u014f\7?\2\2\u014f\u0153\7?\2\2\u0150"+
		"\u0151\7#\2\2\u0151\u0153\7?\2\2\u0152\u0143\3\2\2\2\u0152\u0145\3\2\2"+
		"\2\u0152\u0147\3\2\2\2\u0152\u0148\3\2\2\2\u0152\u0149\3\2\2\2\u0152\u014a"+
		"\3\2\2\2\u0152\u014c\3\2\2\2\u0152\u014e\3\2\2\2\u0152\u0150\3\2\2\2\u0153"+
		"E\3\2\2\2\u0154\u0155\7\60\2\2\u0155G\3\2\2\2\u0156\u0157\7p\2\2\u0157"+
		"\u0158\7w\2\2\u0158\u0159\7n\2\2\u0159\u015a\7n\2\2\u015aI\3\2\2\2\u015b"+
		"\u015c\7v\2\2\u015c\u015d\7t\2\2\u015d\u015e\7w\2\2\u015e\u0165\7g\2\2"+
		"\u015f\u0160\7h\2\2\u0160\u0161\7c\2\2\u0161\u0162\7n\2\2\u0162\u0163"+
		"\7u\2\2\u0163\u0165\7g\2\2\u0164\u015b\3\2\2\2\u0164\u015f\3\2\2\2\u0165"+
		"K\3\2\2\2\u0166\u0167\5T)\2\u0167\u016b\7\60\2\2\u0168\u016a\t\7\2\2\u0169"+
		"\u0168\3\2\2\2\u016a\u016d\3\2\2\2\u016b\u0169\3\2\2\2\u016b\u016c\3\2"+
		"\2\2\u016c\u016f\3\2\2\2\u016d\u016b\3\2\2\2\u016e\u0170\5R(\2\u016f\u016e"+
		"\3\2\2\2\u016f\u0170\3\2\2\2\u0170\u017f\3\2\2\2\u0171\u0173\7\60\2\2"+
		"\u0172\u0174\t\7\2\2\u0173\u0172\3\2\2\2\u0174\u0175\3\2\2\2\u0175\u0173"+
		"\3\2\2\2\u0175\u0176\3\2\2\2\u0176\u0178\3\2\2\2\u0177\u0179\5R(\2\u0178"+
		"\u0177\3\2\2\2\u0178\u0179\3\2\2\2\u0179\u017f\3\2\2\2\u017a\u017c\5T"+
		")\2\u017b\u017d\5R(\2\u017c\u017b\3\2\2\2\u017c\u017d\3\2\2\2\u017d\u017f"+
		"\3\2\2\2\u017e\u0166\3\2\2\2\u017e\u0171\3\2\2\2\u017e\u017a\3\2\2\2\u017f"+
		"M\3\2\2\2\u0180\u0184\7$\2\2\u0181\u0183\n\t\2\2\u0182\u0181\3\2\2\2\u0183"+
		"\u0186\3\2\2\2\u0184\u0182\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u0187\3\2"+
		"\2\2\u0186\u0184\3\2\2\2\u0187\u0188\7$\2\2\u0188O\3\2\2\2\u0189\u018b"+
		"\t\4\2\2\u018a\u0189\3\2\2\2\u018b\u018c\3\2\2\2\u018c\u018a\3\2\2\2\u018c"+
		"\u018d\3\2\2\2\u018d\u0191\3\2\2\2\u018e\u0190\t\5\2\2\u018f\u018e\3\2"+
		"\2\2\u0190\u0193\3\2\2\2\u0191\u018f\3\2\2\2\u0191\u0192\3\2\2\2\u0192"+
		"\u019d\3\2\2\2\u0193\u0191\3\2\2\2\u0194\u0198\7\60\2\2\u0195\u0197\t"+
		"\5\2\2\u0196\u0195\3\2\2\2\u0197\u019a\3\2\2\2\u0198\u0196\3\2\2\2\u0198"+
		"\u0199\3\2\2\2\u0199\u019c\3\2\2\2\u019a\u0198\3\2\2\2\u019b\u0194\3\2"+
		"\2\2\u019c\u019f\3\2\2\2\u019d\u019b\3\2\2\2\u019d\u019e\3\2\2\2\u019e"+
		"Q\3\2\2\2\u019f\u019d\3\2\2\2\u01a0\u01a2\t\f\2\2\u01a1\u01a3\t\r\2\2"+
		"\u01a2\u01a1\3\2\2\2\u01a2\u01a3\3\2\2\2\u01a3\u01a5\3\2\2\2\u01a4\u01a6"+
		"\t\7\2\2\u01a5\u01a4\3\2\2\2\u01a6\u01a7\3\2\2\2\u01a7\u01a5\3\2\2\2\u01a7"+
		"\u01a8\3\2\2\2\u01a8S\3\2\2\2\u01a9\u01b2\7\62\2\2\u01aa\u01ae\t\16\2"+
		"\2\u01ab\u01ad\t\7\2\2\u01ac\u01ab\3\2\2\2\u01ad\u01b0\3\2\2\2\u01ae\u01ac"+
		"\3\2\2\2\u01ae\u01af\3\2\2\2\u01af\u01b2\3\2\2\2\u01b0\u01ae\3\2\2\2\u01b1"+
		"\u01a9\3\2\2\2\u01b1\u01aa\3\2\2\2\u01b2U\3\2\2\2\'\2\3\4\5^mwz|\u0085"+
		"\u0095\u00c7\u00ec\u00f1\u00f6\u00fd\u0102\u011d\u012a\u0141\u0152\u0164"+
		"\u016b\u016f\u0175\u0178\u017c\u017e\u0184\u018c\u0191\u0198\u019d\u01a2"+
		"\u01a7\u01ae\u01b1\7\7\3\2\6\2\2\b\2\2\7\4\2\7\5\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}