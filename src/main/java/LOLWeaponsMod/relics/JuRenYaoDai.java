package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;

/**
 *   "LOLWeaponsMod:JuRenYaoDai": {
 *     "NAME": "巨人腰带",
 *     "FLAVOR": "巨神族战士的束腰，凡人系上即获山峦之力。",
 *     "DESCRIPTIONS": ["战斗开始时获得10点临时生命"]
 *   },
 */
public class JuRenYaoDai extends CustomRelic {
    public static final String ID = ModHelper.makePath("JuRenYaoDai");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/JuRenYaoDai.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/JuRenYaoDai.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.UNCOMMON;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法


    public JuRenYaoDai() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new JuRenYaoDai();
    }
    // 遗物效果，可在此处添加代码 TODO
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(p, this));
        this.addToBot(new AddTemporaryHPAction(p, p, 10));
    }
}
