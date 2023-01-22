package lisp.eval.graphics;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class GraphicsCanvasClear implements Subroutine {

    /**
     * <p>渡されたフレームの画面を初期化する</p>
     * <p>(draw-clear display)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 処理を行うフレーム
     * @throws LispException
     */
    public SExpression apply(SExpression sexp)throws LispException{
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 1) {
            throw new LispException("手続きcanvas-clearの引数は1個である必要があります");
        }

        SExpression display = sexpList.get(0);
        sexpList.remove(0);

        if (!(display instanceof Display)) {
            throw new LispException("手続きcanvas-clearの第一引数はDisplayである必要があります");
        }


        ((Display) display).getFrame().getPanel().drawClear();
        return display;
    }

    @Override
    public String toString() {
        return "#<subr canvas-clear>";
    }
}
