package fr.taeron.shadow.checks;

import org.bukkit.event.Listener;

public class Check
  implements Listener
{
  private String name;
  private CheatType type;
  private int violationstoalert;
  private int maxviolations;
  private boolean instantban;
  private boolean enabled;
  private int maxping;
  private boolean judgementDay;
  private boolean isAutoBan;
  private int violationsToJday;
  private boolean banTimer;
  
  public Check()
  {
    setMaximumViolation(8);
    setViolationsToAlert(0);
    setInstantBan(false);
    this.maxping = 350;
    this.judgementDay = false;
    this.violationsToJday = 4;
    setEnabled(true);
    setAutoban(true);
    setBanTimer(true);
  }
  
  public void setAutoban(boolean b)
  {
    this.isAutoBan = b;
  }
  
  public void setBanTimer(boolean b)
  {
    this.banTimer = b;
  }
  
  public boolean hasBanTimer()
  {
    return this.banTimer;
  }
  
  public void setViolationsToJday(int i)
  {
    this.violationsToJday = i;
  }
  
  public int getViolationsToJday()
  {
    return this.violationsToJday;
  }
  
  public boolean isAutoBan()
  {
    return this.isAutoBan;
  }
  
  public void setMaxPing(int i)
  {
    this.maxping = i;
  }
  
  public void setJudgementDay(Boolean b)
  {
    this.judgementDay = b.booleanValue();
  }
  
  public boolean isJudgementDay()
  {
    return this.judgementDay;
  }
  
  public int getMaxPing()
  {
    return this.maxping;
  }
  
  public boolean isEnabled()
  {
    return this.enabled;
  }
  
  public void setEnabled(boolean b)
  {
    this.enabled = b;
  }
  
  public boolean isInstantBan()
  {
    return this.instantban;
  }
  
  public void setInstantBan(boolean b)
  {
    this.instantban = b;
  }
  
  public String getCheckName()
  {
    return this.name;
  }
  
  public CheatType getCheckType()
  {
    return this.type;
  }
  
  public int getViolationsToAlert()
  {
    return this.violationstoalert;
  }
  
  public int getMaximimViolations()
  {
    return this.maxviolations;
  }
  
  public void setViolationsToAlert(int i)
  {
    this.violationstoalert = i;
  }
  
  public void setMaximumViolation(int i)
  {
    this.maxviolations = i;
  }
  
  public void setName(String n)
  {
    this.name = n;
  }
  
  public void setType(CheatType t)
  {
    this.type = t;
  }
}
