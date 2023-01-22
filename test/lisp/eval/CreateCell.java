package lisp.eval;

import lisp.eval.ConsCell;
import lisp.eval.EmptyList;
import lisp.eval.SExpression;
/*=================================================
テストケース作成時にConsCellの作成が面倒くさい&見にくいから作った。

---------------------------------------------------
ConsCellの作り方

引数の数は自由に設定できる（はず）。

EX) 引数1
ConsCell cell = CreateCell.GetCell(
				(第一引数)
		);

EX) 引数3
ConsCell cell = CreateCell.GetCell(
				(第一引数),
				(第二引数),
				(第三引数)
		);
でもOK。
---------------------------------------------------

---------------------------------------------------------------------
そもそもConsCellって？ .... テストケースでは、とりあえず引数に入れるやつ
EX) TestAdd

       これ↓
ConsCell cell = CreateCell.GetCell(
				Float.valueOf(f1),
				Float.valueOf(f2),
				Int.valueOf(i1)
		);

                            これ↓
assertThat(((Float)add.result(cell)).getValue()).isEqualTo(expected);
------------------------------------------------------------------------

---------------------------------------------------------------------
てかテストケースってどうやんの？... 基本的にはassertThatをつかって確認

EX) TestAdd 
(日本文がコメントアウトされてないから、コピペの実行は出来ないtest/lisp/evalに同じのがある)
目印でコメントには============================がついてる

package lisp.eval; 

import lisp.eval.*;
import lisp.eval.Float;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

[IntelliJ]で書いたから上のやつらは違うかもしれない=========================

public class TestAdd {

	@Test <= これでテストの実行が出来る====================================
	public void test() {
		float f1 = 10.0f;  <=                 ============================
		float f2 = 5.0f;   <=  こいつらが引数の要素  ======================
		int   i1 = 10;     <=                  ===========================

		int expected = 25; <= 期待する値  =================================

		ConsCell cell = CreateCell.GetCell( <= 引数をConsCellにする========
				Float.valueOf(f1),
				Float.valueOf(f2),
				Int.valueOf(i1)
		);


		System.out.println("cell is " + cell);

		Subroutine add = new Add();   <= テストを行いたいクラスのインスタンスを生成==========-
		System.out.println("result is " + (add.result(cell)).toString());
		assertThat(((Int)add.result(cell)).getValue()).isEqualTo(expected);  <=テストを行う ===========
	}

}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
assertThat(メソッドを実行).isEqualTo(期待する値);
[メソッドを実行] == [期待する値]ならテスト成功
......................................................................
EX) int Add1 (int i){ return i+1; }のとき

assertThat(Add1(2)).isEqualTo(3);

2 + 1 = 3なので、
[メソッドを実行] => Add1(2)
[期待する値]     => Add1(2)の期待する返り値(今回は3)

........................................................................
EX) SExpression result(SExpression sec) (Addクラス)の時

assertThat(((Int)add.result(cell)).getValue()).isEqualTo(expected);

[メソッドを実行] => ((Int)add.result(cell)).getValue()
今回は ConsCell(引数)が、(10.0f 5.0f 10)なので(少数ではないので)、Intで帰ってくる    => (Int)add.result(cell)
今回は このAddクラスの resultメソッドは Intクラスを返す(数値を返してない)->数値を返す => ((Int)add.result(cell)).getValue()

[期待する値]     => expected
int expected = 25;

引数は(10.0f 5.0f 10)なので、10.0 + 5.0 + 10 = 25.0 => expected = 25.0
今回は整数(少数以下が0)なのでIntになる               => int expected = 25;
------------------------------------------------------------------------

==================================================*/
public class CreateCell {

    static public ConsCell GetCell(SExpression... sec){
        ConsCell cell = AddCell(sec,0);
        return cell;
    }

    static private ConsCell AddCell(SExpression[] sec, int elem){
        if(elem < sec.length -1 ) return ConsCell.getInstance(sec[elem],AddCell(sec,++elem));
        else                      return ConsCell.getInstance(sec[elem],EmptyList.getInstance());
    }
}
