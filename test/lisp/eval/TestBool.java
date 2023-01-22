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

public class TestBool {
    Reader createReader(String stdin) {
        StringReader sr = new StringReader(stdin);
        BufferedReader br = new BufferedReader(sr);
        return new Reader(br);
    }

    @Test
    public void 真の真理値を扱える() throws IOException, LispException {
        String stdin = "#t";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Bool.valueOf(true);
        assertThat(actual, is(expected));
    }

    @Test
    public void 偽の真理値を扱える() throws IOException, LispException {
        String stdin = "#f";

        Environment env = SetUp.getGlobalEnvironment();
        Reader reader = createReader(stdin);
        SExpression sexp = reader.read();

        SExpression actual = Evaluator.eval(sexp, env);

        System.out.println("lisp> " + stdin);
        System.out.println(actual);

        SExpression expected = Bool.valueOf(false);
        assertThat(actual, is(expected));
    }
}
