/*
    Copyright (c) 2010, NullNoname
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

        * Redistributions of source code must retain the above copyright
          notice, this list of conditions and the following disclaimer.
        * Redistributions in binary form must reproduce the above copyright
          notice, this list of conditions and the following disclaimer in the
          documentation and/or other materials provided with the distribution.
        * Neither the name of NullNoname nor the names of its
          contributors may be used to endorse or promote products derived from
          this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
    AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
    IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
    ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
    LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
    CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
    SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
    INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
    CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
    ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
    POSSIBILITY OF SUCH DAMAGE.
*/
package mu.nu.nullpo.game.subsystem.wallkick;

import mu.nu.nullpo.game.component.Block;
import mu.nu.nullpo.game.component.Controller;
import mu.nu.nullpo.game.component.Field;
import mu.nu.nullpo.game.component.Piece;
import mu.nu.nullpo.game.component.WallkickResult;

/**
 * ClassicWallkick - Classic rulesWallkick (OldVersionOfCLASSIC1And2Equivalent)
 */
public class ClassicWallkick implements Wallkick {
	/*
	 * Wallkick
	 */
	public WallkickResult executeWallkick(int x, int y, int rtDir, int rtOld, int rtNew, boolean allowUpward, Piece piece, Field field, Controller ctrl) {
		int check = 0;
		if(piece.big) check = 1;

		// NormalWallkick (IOther)
		if(piece.id != Piece.PIECE_I) {
			if(checkCollisionKick(piece, x, y, rtNew, field) || (piece.id == Piece.PIECE_I2) || (piece.id == Piece.PIECE_L3)) {
				int temp = 0;

				if(!piece.checkCollision(x - 1 - check, y, rtNew, field)) temp = -1 - check;
				if(!piece.checkCollision(x + 1 + check, y, rtNew, field)) temp = 1 + check;

				if(temp != 0) {
					return new WallkickResult(temp, 0, rtNew);
				}
			}
		}

		return null;
	}

	/**
	 * WallkickIt is possible to examine whether
	 * @param piece BlockPeace
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param rt Direction
	 * @param fld field
	 * @return WallkickIf possibletrue
	 */
	private boolean checkCollisionKick(Piece piece, int x, int y, int rt, Field fld) {
		// BigThe only treatment
		if(piece.big == true) return checkCollisionKickBig(piece, x, y, rt, fld);

		for(int i = 0; i < piece.getMaxBlock(); i++) {
			if(piece.dataX[rt][i] != 1 + piece.dataOffsetX[rt]) {
				int x2 = x + piece.dataX[rt][i];
				int y2 = y + piece.dataY[rt][i];

				if(x2 >= fld.getWidth()) {
					return true;
				}
				if(y2 >= fld.getHeight()) {
					return true;
				}
				if(fld.getCoordAttribute(x2, y2) == Field.COORD_WALL) {
					return true;
				}
				if((fld.getCoordAttribute(x2, y2) != Field.COORD_VANISH) && (fld.getBlockColor(x2, y2) != Block.BLOCK_COLOR_NONE)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * WallkickIt is possible to examine whether (BigFor)
	 * @param piece BlockPeace
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param rt Direction
	 * @param fld field
	 * @return WallkickIf possibletrue
	 */
	private boolean checkCollisionKickBig(Piece piece, int x, int y, int rt, Field fld) {
		for(int i = 0; i < piece.getMaxBlock(); i++) {
			if(piece.dataX[rt][i] != 1 + piece.dataOffsetX[rt]) {
				int x2 = (x + piece.dataX[rt][i] * 2);
				int y2 = (y + piece.dataY[rt][i] * 2);

				// 4BlockMinutes to examine
				for(int k = 0; k < 2; k++)for(int l = 0; l < 2; l++) {
					int x3 = x2 + k;
					int y3 = y2 + l;

					if(x3 >= fld.getWidth()) {
						return true;
					}
					if(y3 >= fld.getHeight()) {
						return true;
					}
					if(fld.getCoordAttribute(x3, y3) == Field.COORD_WALL) {
						return true;
					}
					if((fld.getCoordAttribute(x3, y3) != Field.COORD_VANISH) && (fld.getBlockColor(x3, y3) != Block.BLOCK_COLOR_NONE)) {
						return true;
					}
				}
			}
		}

		return false;
	}
}
