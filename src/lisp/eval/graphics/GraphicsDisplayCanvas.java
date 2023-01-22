package lisp.eval.graphics;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class GraphicsDisplayCanvas implements SpecialForm{

    /**
     * <p>ディスプレイを表示する</p>
     * <p>(display title width height)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return フレームを保持したS式
     * @throws LispException
     */
    public SExpression apply(SExpression sexp, Environment env) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 3) {
            throw new LispException("手続きdisplayの引数は3個である必要があります");
        }

        SExpression title = sexpList.get(0);
        SExpression width = Evaluator.eval(sexpList.get(1), env);
        SExpression height = Evaluator.eval(sexpList.get(2), env);

        if (!(title instanceof Symbol)){
            throw new LispException("手続きdisplayの第一引数はシンボルである必要があります");
        }

        if (!(width instanceof Int && height instanceof Int)) {
            throw new LispException("手続きdisplayの第二引数と第三引数はInt型である必要があります");
        }

        return Display.getInstance((Symbol) title, (Int) width, (Int) height);
    }

    @Override
    public String toString() {
        return "#<syntax display>";
    }
}
