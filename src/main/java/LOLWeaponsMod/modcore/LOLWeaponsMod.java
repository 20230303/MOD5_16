package LOLWeaponsMod.modcore;

import LOLWeaponsMod.characters.MyCharacter;
import LOLWeaponsMod.relics.MyRelic;
import LOLWeaponsMod.cards.AbstractExampleCard;
import LOLWeaponsMod.relics.XingShi;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;

import static LOLWeaponsMod.characters.MyCharacter.PlayerColorEnum.MY_CHARACTER;

import static LOLWeaponsMod.characters.MyCharacter.PlayerColorEnum.EXAMPLE_GREEN;
import static com.megacrit.cardcrawl.core.Settings.language;

/**
@SpireInitializer // 加载mod的注解
public class LOLWeaponsMod {
    // 构造方法
    public LOLWeaponsMod() {
    }

    // 注解需要调用的方法，必须写
    public static void initialize() {
        new LOLWeaponsMod();
    }
}
 **/

// 实现接口
@SpireInitializer
public class LOLWeaponsMod implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber { // 实现接口
    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "PEACH_LOLWeaponsModResources/img/char/Character_Button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "PEACH_LOLWeaponsModResources/img/char/Character_Portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "PEACH_LOLWeaponsModResources/img/512/bg_attack_512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "PEACH_LOLWeaponsModResources/img/512/bg_power_512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "PEACH_LOLWeaponsModResources/img/512/bg_skill_512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "PEACH_LOLWeaponsModResources/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "PEACH_LOLWeaponsModResources/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "PEACH_LOLWeaponsModResources/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "PEACH_LOLWeaponsModResources/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "PEACH_LOLWeaponsModResources/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = "PEACH_LOLWeaponsModResources/img/char/cost_orb.png";

    //添加新颜色
    //除以255得出需要的参数，也可以写计算值
    public static final Color MY_COLOR = new Color(166.0F / 255.0F, 136.0F / 255.0F, 116.0F / 255.0F, 1.0F);
    public LOLWeaponsMod() {
        BaseMod.subscribe(this); // 告诉basemod你要订阅事件
        // 这里注册颜色
        BaseMod.addColor(EXAMPLE_GREEN, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENEYGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,SMALL_ORB);
    }

    public static void initialize() {// 告诉basemod你要初始化mod
        new LOLWeaponsMod();
    }


    /**
     * 以下是注册事件的函数
     * 这些函数会在basemod加载mod时调用，并在游戏运行时触发相应事件
     */

    // 当basemod开始注册mod卡牌时，便会调用这个函数
    @Override
    public void receiveEditCards() {
        // TODO 这里写添加你卡牌的代码
        // 向basemod注册卡牌
        //BaseMod.addCard(new Strike()); // 注册Strike卡牌,
        //挨个注册太麻烦，可以用json加载卡牌
        new AutoAdd("LOLWeaponsMod") // 这里填写你在ModTheSpire.json中写的modid
                .packageFilter(AbstractExampleCard.class) // 寻找所有和此类同一个包及内部包的类（本例子是所有卡牌）
                .setDefaultSeen(true) // 是否将卡牌标为可见
                .cards(); // 开始批量添加卡牌
    }


    // 当basemod开始注册mod本地化内容时，便会调用这个函数
    public void receiveEditStrings() {// 注册本地化文本
        String lang;
        if (language == Settings.GameLanguage.ZHS) {
            lang = "ZHS"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
        } else {
            lang = "ENG"; // 如果没有相应语言的版本，默认加载英语
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "PEACH_LOLWeaponsModResources/localization/" + lang + "/cards.json");
        // 加载相应语言的卡牌本地化内容。
        // 如果是中文，加载的就是"PEACH_LOLWeaponsModResources/localization/ZHS/cards.json"
        // 这里添加注册角色本地化文本
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "PEACH_LOLWeaponsModResources/localization/" + lang + "/characters.json");
        //
        // 添加注册遗物本地化内容
        BaseMod.loadCustomStringsFile(RelicStrings.class, "PEACH_LOLWeaponsModResources/localization/" + lang + "/relics.json");
        //注册能力本地化内容
        BaseMod.loadCustomStringsFile(PowerStrings.class, "PEACH_LOLWeaponsModResources/localization/" + lang + "/powers.json");
        //怪物本地化内容
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "PEACH_LOLWeaponsModResources/localization/" + lang + "/monsters.json");

    }


    @Override
    public void receiveEditCharacters() {// 注册角色
        // 注册新角色
        //BaseMod.addCharacter(new MyCharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, MY_CHARACTER);
    }


    @Override
    public void receiveEditRelics() {// 注册遗物
        //TODO 这里写添加你遗物的代码
        // 向basemod注册遗物
        //BaseMod.addRelic(new MyRelic(), RelicType.SHARED); // RelicType表示是所有角色都能拿到的遗物，还是一个角色的独有遗物
        //注释掉，用json加载遗物，避免重复注册
        //用json加载遗物
        new AutoAdd("LOLWeaponsMod") // 这里填写你在ModTheSpire.json中写的modid
               .packageFilter(XingShi.class) // 寻找所有和此类同一个包及内部包的类（本例子是所有遗物）
               .setDefaultSeen(true) // 是否将遗物标为可见
                .any(CustomRelic.class, (info, relic) -> {// 开始批量添加遗物
                    BaseMod.addRelic(relic, RelicType.SHARED);// 注册所有角色都能拿到的遗物
                    if (info.seen) {// 是否将遗物标为已发现
                        UnlockTracker.markRelicAsSeen(relic.relicId);// 标记遗物为已发现
                    }
                });


    }

    @Override
    public void receiveEditKeywords() {
        //自定义关键字
        //BaseMod.addKeyword("LOLWeaponsMod", "流血", new String[] {"流血"}, "拥有 #y流血 的角色在受到伤害时失去等量生命。");
        /**
         * addKeyword(String modID, String proper, String[] names, String description)
         *
         * modID:你的mod的id，用于和其他mod的关键词区分。当你使用modID时，你的关键词需要加上前缀，如："LOLWeaponsMod:流血"。
         * proper:关键词的正确名称，显示在关键词提示框中。
         * names:所有能别识别的名称，例如，如果你proper设置为“法术（X）”，names设置为“法术”，“法术的”，那么描述中“LOLWeaponsMod:法术”和“LOLWeaponsMod:法术的”都会被识别为该关键词，提示框的标题为“法术（X）”。
         * description:关键词描述。
         *
         * 理论上，这样可以添加关键词，但是修改起来十分麻烦，还可能因为硬编码被群里的作者打。这里提供一种使用json加载关键词的方式
         */

        Gson gson = new Gson();
        String lang = "eng";
        if (language == Settings.GameLanguage.ZHS) {
            lang = "zhs";
        }

        String json = Gdx.files.internal("PEACH_LOLWeaponsModResources/localization/Keywords_" + lang + ".json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                // 这个id要全小写
                BaseMod.addKeyword("lolweaponsmod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    // 注册怪物组合
//    @Override
//    public void receivePostInitialize() {
//        receiveEditMonsters();
//    }
//    private void receiveEditMonsters() {
//        // 注册怪物组合，你可以多添加几个怪物
//        BaseMod.addMonster("LOLWeaponsMod:MyMonster", MyMonster.NAME, () -> new MyMonster(0.0F, 0.0F));
//        // 两个异鸟
//        // BaseMod.addMonster("LOLWeaponsMod:2 Byrds", "", () -> new MonsterGroup(new AbstractMonster[] { new Byrd(-80.0F, MathUtils.random(25.0F, 70.0F)), new Byrd(200.0F, MathUtils.random(25.0F, 70.0F)) }));
//
//        // 添加战斗遭遇
//        // 在第二章添加精英遭遇，权重为1.0，权重越高越可能遇到
//        BaseMod.addEliteEncounter("TheCity", new MonsterInfo("LOLWeaponsMod:MyMonster", 1.0F));
//    }

}

