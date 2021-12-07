// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: Darkan
//
package com.rs.game.player.content.dialogue;

import java.util.function.Supplier;

public class Option {
	
	private Dialogue dialogue;
	private Supplier<Boolean> constraint;
	
	public Option(Supplier<Boolean> constraint, Dialogue dialogue) {
		this.constraint = constraint;
		this.dialogue = dialogue;
	}
	
	public Option(Dialogue dialogue) {
		this(null, dialogue);
	}

	public Dialogue getDialogue() {
		return dialogue;
	}

	public Supplier<Boolean> getConstraint() {
		return constraint;
	}

	public boolean show() {
		return constraint == null ? true : constraint.get();
	}

}