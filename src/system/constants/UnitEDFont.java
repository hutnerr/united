package system.constants;

import java.awt.Font;

/**
 * Helper class to contain our custom fonts.
 * 
 * @author Hunter Baker
 * @version 5/6/2024
 */
public class UnitEDFont
{
  // Naming Convention
  // FontSizeStyle
  // EX: SS22B = SansSerif 22FontSize Bold

  public static final Font MS12B = new Font(Font.MONOSPACED, Font.BOLD, 12);

  // Sans Serifs
  private static final String MAIN_FONT = "SansSerif";
  public static final Font SS22B = new Font(MAIN_FONT, Font.BOLD, 22);
  public static final Font SS18B = new Font(MAIN_FONT, Font.BOLD, 18);
  public static final Font SS16B = new Font(MAIN_FONT, Font.BOLD, 16);
  public static final Font SS12B = new Font(MAIN_FONT, Font.BOLD, 12);
  public static final Font SS10B = new Font(MAIN_FONT, Font.BOLD, 10);
}
