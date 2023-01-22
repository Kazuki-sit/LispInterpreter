package lisp.eval;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import lisp.SetUp;
import lisp.exception.LispException;
import lisp.reader.Reader;

public class TestFloat {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void 浮動小数点を扱える() throws IOException, LispException {
        String stdin = "1.1";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Float.valueOf(1.1f);
        assertThat(actual, is(expected));
    }

    @Test
    public void 正の浮動小数点を扱える() throws IOException, LispException {
        String stdin = "+2.2";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Float.valueOf(2.2f);
        assertThat(actual, is(expected));
    }

    @Test
    public void 負の浮動小数点を扱える() throws IOException, LispException {
        String stdin = "-3.3";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Float.valueOf(-3.3f);
        assertThat(actual, is(expected));
    }
}
