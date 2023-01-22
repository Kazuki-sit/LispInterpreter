package lisp.eval.specialform;

/*
 * (if bool s_true s_false) bool が　true なら s_true false　なら s_falseのS式を行う
 *
 */

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Loop implements SpecialForm {

    public SExpression apply(SExpression sexp, Environment env) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() == 0) {
            throw new LispException("if Exception ! :: Argument num is wrong (expected more 0 but " + sexpList.size() + ")");
        }

        while (true) {
            for (SExpression se : sexpList)
                Evaluator.eval(se, env);
        }
    }

}
