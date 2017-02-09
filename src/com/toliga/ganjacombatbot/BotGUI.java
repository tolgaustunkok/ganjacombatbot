package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.core.Utilities;
import com.toliga.ganjabots.core.Validator;
import com.toliga.ganjacombatbot.rules.SeperableTextValidator;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;

import javax.swing.*;
import java.awt.*;

public class BotGUI extends JFrame {
    private JButton btnStart;
    private JButton btnStop;
    private JTabbedPane tabbedPaneMenu;
    private JPanel rootPane;
    private JTextField mobNameTextField;
    private JCheckBox powerkillCheckBox;
    private JCheckBox lootCheckBox;
    private JCheckBox eatFoodCheckBox;
    private JCheckBox usePotionCheckBox;
    private JCheckBox useSpecialAttackCheckBox;
    private JButton button1;
    private JTextArea textArea1;
    private JTextField lootTextField;
    private JCheckBox bankWhenFullCheckBox;
    private JCheckBox logoutWhenFullCheckBox;
    private JCheckBox buryBonesCheckBox;
    private JTextField foodTextField;
    private JSlider healthSlider;
    private JTextField foodAmountTextField;
    private JCheckBox combatCheckBox;
    private JCheckBox strengthCheckBox;
    private JCheckBox defenceCheckBox;
    private JCheckBox attackCheckBox;
    private JCheckBox superStrengthCheckBox;
    private JCheckBox superDefenceCheckBox;
    private JCheckBox superAttackCheckBox;
    private JTextField textField5;
    private JLabel infoLabel;
    private JPanel lootingPanel;
    private JPanel foodPanel;
    private JPanel potionPanel;
    private JCheckBox superCombatCheckBox;
    private JCheckBox useAntibanCheckBox;
    private JSlider mouseSlider;
    private JSlider cameraSlider;
    private JSlider tabSlider;
    private JCheckBox interactionResponseCheckBox;
    private JTextField textField1;
    private JButton applyNowButton;
    private GanjaCombatBotMain context;
    private Image backgroundImage;
    private Image ganjaIcon;
    private SaveManager saveManager;
    private Validator validator;

    public BotGUI(AbstractScript context, String title) {
        this.context = (GanjaCombatBotMain) context;
        saveManager = new SaveManager();
        validator = new Validator();
        validator.addValidation(new SeperableTextValidator());
        setTitle(title);
        setContentPane(rootPane);
        pack();
        //setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        initializeComponents();
        registerEvents();
    }

    private void initializeComponents() {
        backgroundImage = Utilities.LoadImage("http://i63.tinypic.com/2j48v94.png", 230, 111);
        ganjaIcon = Utilities.LoadImage("http://ai-i1.infcdn.net/icons_siandroid/png/200/1138/1138962.png", 20, 20);
        btnStart.setIcon(new ImageIcon(Utilities.LoadImage("http://cdn3.iconfinder.com/data/icons/buttons/512/Icon_3-128.png", 35, 35)));
        btnStop.setIcon(new ImageIcon(Utilities.LoadImage("http://cdn3.iconfinder.com/data/icons/buttons/512/Icon_5-128.png", 35, 35)));
        infoLabel.setIcon(new ImageIcon(Utilities.LoadImage("http://cdn3.iconfinder.com/data/icons/buttons/512/Icon_17-128.png", 20, 20)));
        btnStop.setEnabled(false);
        tabbedPaneMenu.setEnabledAt(1, false);
        tabbedPaneMenu.setEnabledAt(2, false);
        tabbedPaneMenu.setEnabledAt(3, false);
        tabbedPaneMenu.setEnabledAt(4, false);

        if (GlobalSettings.MOB_NAMES != null) {
            for (String mob : GlobalSettings.MOB_NAMES) {
                mobNameTextField.setText(mobNameTextField.getText() + mob + ",");
            }
        }

        if (GlobalSettings.LOOT_NAMES != null) {
            for (String loot : GlobalSettings.LOOT_NAMES) {
                lootTextField.setText(lootTextField.getText() + loot + ",");
            }
        }

        if (GlobalSettings.FOOD_NAMES != null) {
            for (String food : GlobalSettings.FOOD_NAMES) {
                foodTextField.setText(foodTextField.getText() + food + ",");
            }
        }
    }

    private void registerEvents() {
        btnStart.addActionListener(event -> {
            /********************MOB NAMES*******************/
            String mobs[] = validator.validate(mobNameTextField.getText()).split(",");

            for (int i = 0; i < mobs.length; i++) {
                mobs[i] = mobs[i].trim();
            }

            GlobalSettings.MOB_NAMES = mobs;

            /******************LOOT NAMES*******************/
            if (GlobalSettings.LOOT) {
                String loots[] = validator.validate(lootTextField.getText()).split(",");

                for (int i = 0; i < loots.length; i++) {
                    loots[i] = loots[i].trim();
                }

                GlobalSettings.LOOT_NAMES = loots;
            }

            /*******************FOOD NAMES*******************/
            if (GlobalSettings.EAT_FOOD) {
                String foods[] = validator.validate(foodTextField.getText()).split(",");

                for (int i = 0; i < foods.length; i++) {
                    foods[i] = foods[i].trim();
                }

                GlobalSettings.FOOD_NAMES = foods;
                GlobalSettings.FOOD_AMOUNT = foodAmountTextField.getText().isEmpty() ? 0 : Integer.parseInt(foodAmountTextField.getText());
                GlobalSettings.HEALTH_PERCENT = healthSlider.getValue();
            }

            context.setStarted(true);
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                saveManager.save();
            }
        });

        btnStop.addActionListener(event -> {
            context.setStarted(false);
            btnStop.setEnabled(false);
            btnStart.setEnabled(true);
        });

        healthSlider.addChangeListener(event -> {
            GlobalSettings.HEALTH_PERCENT = ((JSlider) event.getSource()).getValue();
        });

        powerkillCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.POWERKILL = source.isSelected();
            if (source.isSelected()) {
                lootCheckBox.setSelected(!source.isSelected());
            }
            // lootCheckBox.setEnabled(!source.isSelected());
        });

        lootCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.LOOT = source.isSelected();
            if (source.isSelected()) {
                powerkillCheckBox.setSelected(!source.isSelected());
            }
            //powerkillCheckBox.setEnabled(!source.isSelected());
            tabbedPaneMenu.setEnabledAt(1, source.isSelected());
        });

        eatFoodCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.EAT_FOOD = source.isSelected();
            tabbedPaneMenu.setEnabledAt(2, source.isSelected());
        });

        bankWhenFullCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.BANK_WHEN_FULL = source.isSelected();
        });

        logoutWhenFullCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.LOGOUT_WHEN_FULL = source.isSelected();
        });

        useAntibanCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            tabbedPaneMenu.setEnabledAt(4, source.isSelected());
            GlobalSettings.USE_ANTIBAN = source.isSelected();
            if (source.isSelected()) {
                context.getAntibanManager().activateAllFeatures();
            } else {
                context.getAntibanManager().disableAllFeatures();
            }
        });

        buryBonesCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.BURY_BONES = source.isSelected();
        });

        usePotionCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.USE_POTION = source.isSelected();
            tabbedPaneMenu.setEnabledAt(3, source.isSelected());
        });

        combatCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.COMBAT_POTION = source.isSelected();
        });

        strengthCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.STRENGTH_POTION = source.isSelected();
        });

        defenceCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.DEFENCE_POTION = source.isSelected();
        });

        attackCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.ATTACK_POTION = source.isSelected();
        });

        superCombatCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.SUPER_COMBAT_POTION = source.isSelected();
        });

        superStrengthCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.SUPER_STRENGTH_POTION = source.isSelected();
        });

        superDefenceCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.SUPER_DEFENCE_POTION = source.isSelected();
        });

        superAttackCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.SUPER_ATTACK_POTION = source.isSelected();
        });

        mouseSlider.addChangeListener(event -> {
            context.getAntibanManager().getFeature("RANDOM_MOUSE_MOVEMENT").setProbability((float)(mouseSlider.getValue() / 1000.0));
        });

        cameraSlider.addChangeListener(event -> {
            context.getAntibanManager().getFeature("RANDOM_CAMERA_ROTATION").setProbability((float)(cameraSlider.getValue() / 1000.0));
        });

        tabSlider.addChangeListener(event -> {
            context.getAntibanManager().getFeature("RANDOM_TAB_CHECKING").setProbability((float)(tabSlider.getValue() / 1000.0));
        });

        interactionResponseCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            context.getAntibanManager().getFeature("INTERACTION_RESPONSE").setEnabled(source.isSelected());
        });
    }

    public void DrawInGameGUI(Graphics2D graphics) {
        int     xhAtk = context.getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK),
                xhStr = context.getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH),
                xhDef = context.getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE),
                xhHit = context.getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS),
                xhRan = context.getSkillTracker().getGainedExperiencePerHour(Skill.RANGED),
                xhMag = context.getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC);

        long    xgAtk = context.getSkillTracker().getGainedExperience(Skill.ATTACK),
                xgStr = context.getSkillTracker().getGainedExperience(Skill.STRENGTH),
                xgDef = context.getSkillTracker().getGainedExperience(Skill.DEFENCE),
                xgHit = context.getSkillTracker().getGainedExperience(Skill.HITPOINTS),
                xgRan = context.getSkillTracker().getGainedExperience(Skill.RANGED),
                xgMag = context.getSkillTracker().getGainedExperience(Skill.MAGIC);

        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawImage(backgroundImage, 264, 347, null);

        graphics.setFont(new Font("Magneto", Font.BOLD, 15));
        graphics.setColor(new Color(0x00, 0x66, 0x00));
        graphics.drawString("Ganja Combat Bot", 264, 365);

        graphics.setFont(new Font("Consolas", Font.PLAIN, 15));
        graphics.setColor(Color.BLACK);

        graphics.drawString("v" + GanjaCombatBotMain.VERSION, 420, 365);
        graphics.drawImage(ganjaIcon, 470, 347, null);

        graphics.drawString("        Run Time:", 280, 382);
        graphics.drawString("           XP/hr:", 280, 399);
        graphics.drawString("       XP gained:", 280, 416);
        //graphics.drawString(String.format("Atk: %s  Str: %s  Def: %s",
        //        context.getSkills().getRealLevel(Skill.ATTACK), context.getSkills().getRealLevel(Skill.STRENGTH), context.getSkills().getRealLevel(Skill.DEFENCE)),
        //        280, 433);
        if (GlobalSettings.BURY_BONES) {
            graphics.drawString("Prayer XP gained:", 280, 433);
        }

        graphics.drawString(context.getTimer().formatTime(), 420, 382); // Runtime
        graphics.drawString((xhAtk + xhStr + xhDef + xhHit + xhRan + xhMag) + " XP", 420, 399); // XP / hr
        graphics.drawString((xgAtk + xgStr + xgDef + xgHit + xgRan + xgMag) + " XP", 420, 416); // XP gained
        if (GlobalSettings.BURY_BONES) {
            graphics.drawString(context.getSkillTracker().getGainedExperience(Skill.PRAYER) + " XP", 420, 433); // Prayer XP gained
        }
    }
}
