package org.firstinspires.ftc.teamcode.command;

import java.util.Timer;

class CommandState {
  // Whether or not it is iterruptable.
  private final boolean m_interruptible;

  CommandState(boolean interruptible) {
    m_interruptible = interruptible;
  }

  boolean isIterruptible() {
    return m_interruptible;
  }
}
