package ru.korus.tmis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        19.06.13, 0:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Форматер текста<br>
 * Пример:     public static final TextFormat DEFAULT_FORMAT = new TextFormat("${title}, result: [${result}], trace: [${trace}] [${time} ms]");
 * final Map<String, Object> ctx = getContext();
 * ctx.put("title", title);
 * ctx.put("result", result);
 * ctx.put("trace", trace);
 * ctx.put("time", System.currentTimeMillis() - startTime);
 *
 * String result = DEFAULT_FORMAT.format(ctx);
 */
public class TextFormat {
    private static final Logger logger = LoggerFactory.getLogger(TextFormat.class);

    /**
     * исходный шаблон
     */
    @Nonnull
    private final String format;

    /**
     * шаблон разобранный на список токенов
     */
    @Nonnull
    private final java.util.List<Token> tokens;

    /**
     * регулярное выражения для переменных в шаблоне
     */
    @Nonnull
    private final java.util.regex.Pattern variablePattern;

    /**
     * дефолтный регексп для переменных в шаблоне
     */
    private static final Pattern DEFAULT_VARIABLE_PATTERN = Pattern.compile("\\$\\{(.*?)\\}");

    /**
     * Конструктор шаблона, в котором переменные заданы в виде ${имя}
     *
     * @param format шаблон
     */
    public TextFormat(@Nonnull final String format) {
        this(format, DEFAULT_VARIABLE_PATTERN);
    }

    /**
     * Конструктор шаблона, в котором можно задавать представление переменных через регулярное выражение
     *
     * @param format          шаблон, в котором переменные задаются
     * @param variablePattern регулярное выражение в котором задается шаблон переменной, при применении которого в 1-й группе будет находиться имя переменной
     */
    public TextFormat(@Nonnull final String format, @Nonnull final Pattern variablePattern) {
        this.variablePattern = variablePattern;
        this.format = format;

        final Matcher matcher = variablePattern.matcher(format);

        int pos = 0;
        tokens = new LinkedList<Token>();
        while (matcher.find(pos)) {
            int start = matcher.start();
            if (start > pos) {
                tokens.add(new StringToken(format.substring(pos, start)));
            }

            tokens.add(new VariableToken(matcher.group(1)));

            pos = matcher.end();
        }

        if (pos < format.length()) {
            tokens.add(new StringToken(format.substring(pos)));
        }
    }

    /**
     * Возвращает список найденых переменных в шаблоне, в порядке появления переменной
     *
     * @return список найденых переменных в шаблоне, в порядке появления переменной
     */
    @Nonnull
    public java.util.List<String> getVariables() {
        final List<String> vars = new LinkedList<String>();

        visit(new Token.Visitor() {
            public void onStringToken(final StringToken token) {
            }

            public void onVariableToken(final VariableToken token) {
                vars.add(token.getName());
            }
        });

        return vars;
    }

    /**
     * Форматирование без аргументов
     *
     * @return строка, в которой все переменные заменены пустой строкой
     */
    @Nonnull
    public String format() {
        final StringBuilder builder = new StringBuilder();

        visit(new Token.Visitor() {
            public void onStringToken(final StringToken token) {
                builder.append(token.getString());
            }

            public void onVariableToken(final VariableToken token) {
                // ничего не вставляем
            }
        });

        return builder.toString();
    }

    /**
     * Форматирование со списком аргументов, заданными парами ["имя1", "значение1", "имя2", "значение2"]
     *
     * @param args список аргументов, заданными парами ["имя1", "значение1", "имя2", "значение2"]
     * @return строка, в которой переменные заменены значениями из массива args, а не найденные заменены пустой строкой
     */
    @Nonnull
    public String format(@Nonnull final String... args) {
        final StringBuilder builder = new StringBuilder();

        if (args.length % 2 == 1) {
            throw new IllegalArgumentException("Bad args - size should be even");
        }

        visit(new Token.Visitor() {
            public void onStringToken(final StringToken token) {
                builder.append(token.getString());
            }

            public void onVariableToken(final VariableToken token) {
                for (int i = 0; i < args.length; i += 2) {
                    if (args[i].equals(token.getName())) {
                        builder.append(args[i + 1]);
                    }
                }
            }
        });

        return builder.toString();
    }

    /**
     * Форматирование со переменными, заданными в виде Map, в виде {'имя' => 'значение'}
     *
     * @param args таблица пар 'имя переменной' => 'значение'
     * @return строка, в которой переменные заменены значениями из map, а не найденные заменены пустой строкой
     */
    @Nonnull
    public String format(@Nonnull final java.util.Map<String, ?> args) {
        final StringBuilder builder = new StringBuilder();

        visit(new Token.Visitor() {
            public void onStringToken(final StringToken token) {
                builder.append(token.getString());
            }

            public void onVariableToken(final VariableToken token) {
                final Object value = args.get(token.getName());
                builder.append(value != null ? value.toString() : "");
            }
        });

        return builder.toString();
    }

    /**
     * Форматирование, где значения переменных определяются через NameResolver, которому передается имя переменной
     *
     * @param nameResolver класс для определения значения по имени
     * @return строка, в которой все переменные заменены значениями полученными через NameResolver, а не найденные заменены пустой строкой
     */
    @Nonnull
    public String format(@Nonnull final NameResolver nameResolver) {
        final StringBuilder builder = new StringBuilder();

        visit(new Token.Visitor() {
            public void onStringToken(final StringToken token) {
                builder.append(token.getString());
            }

            public void onVariableToken(final VariableToken token) {
                builder.append(nameResolver.getValue(token.getName()));
            }
        });

        return builder.toString();
    }

    @Nonnull
    public List<String> getVariableNames() {
        final List<String> variableNames = new LinkedList<String>();
        visit(new Token.Visitor() {
            public void onStringToken(final StringToken token) {
            }

            public void onVariableToken(final VariableToken token) {
                variableNames.add(token.getName());
            }
        });
        return variableNames;
    }

    /**
     * Метод используется для валидации шаблона, проверяется что в шаблоне должны быть
     * макропеременные только из указанного множества (но не обязательно все)
     *
     * @param variables разрешенные переменные для шаблона
     * @return true если все переменные шаблона есть в множестве variableNames
     */
    public boolean validate(@Nonnull final java.util.Set<String> variables) {
        final boolean[] ok = new boolean[]{true};
        visit(new Token.Visitor() {
            public void onStringToken(final StringToken token) {
            }

            public void onVariableToken(final VariableToken token) {
                ok[0] = ok[0] && variables.contains(token.getName());
            }
        });

        return ok[0];
    }

    /**
     * Метод провеяет, есть ли искомая переменная в шаблоне
     *
     * @param variableName искомая переменная
     * @return true если переменная найдена, иначе false
     */
    public boolean containsVariable(@Nonnull final String variableName) {
        final boolean[] found = new boolean[]{false};
        visit(new Token.Visitor() {
            @Override
            public void onStringToken(@Nonnull final StringToken token) {
            }

            @Override
            public void onVariableToken(@Nonnull final VariableToken token) {
                found[0] |= token.getName().equals(variableName);
            }
        });
        return found[0];
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TextFormat that = (TextFormat) o;

        return format.equals(that.format) && variablePattern.pattern().equals(that.variablePattern.pattern());
    }

    @Override
    public int hashCode() {
        int result;
        result = variablePattern.hashCode();
        result = 31 * result + format.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return format;
    }

    public interface NameResolver {
        Object getValue(String name);
    }

    private void visit(@Nullable final Token.Visitor visitor) {
        for (final Token token : tokens) {
            token.accept(visitor);
        }
    }

    /**
     * Token interface
     */
    private interface Token {
        void accept(final Visitor visitor);

        interface Visitor {
            void onStringToken(final StringToken token);

            void onVariableToken(final VariableToken token);
        }
    }

    /**
     * String token class
     */
    private static class StringToken implements Token {
        private final String string;

        public StringToken(final String string) {
            this.string = string;
        }

        @Nonnull
        public String getString() {
            return string;
        }

        @Override
        public void accept(@Nonnull final Visitor visitor) {
            visitor.onStringToken(this);
        }

        @Override
        @Nonnull
        public String toString() {
            return string;
        }
    }

    /**
     * Variable token class
     */
    private static class VariableToken implements Token {
        @Nonnull
        private final String name;

        public VariableToken(@Nonnull final String name) {
            this.name = name;
        }

        @Nonnull
        public String getName() {
            return name;
        }

        @Override
        public void accept(@Nonnull final Visitor visitor) {
            visitor.onVariableToken(this);
        }

        @Override
        public String toString() {
            return "${" + name + "}";
        }
    }

}
