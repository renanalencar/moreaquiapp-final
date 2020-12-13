package br.com.renanalencar.moreaqui;

/**
 * This interface denotes objects that are able to execute actions, once they
 * are invoked. It is defined as in the Command design pattern.
 * 
 * @author Fernando
 *
 */
public interface Command {
  /**
   * Performs a series of actions on the input object.
   * 
   * @param d the subject of the actions that will be executed.
   */
  void execute(DaoImpl d);
}
