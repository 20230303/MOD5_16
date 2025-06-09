package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 *   "LOLWeaponsMod:KangMoDouPeng": {
 *     "NAME": "抗魔斗篷",
 *     "FLAVOR": "织入龙鳞的衬里，将诅咒化为拂面微风。",
 *     "DESCRIPTIONS": ["战斗开始时获得2层多层护甲"]
 *   },
 */
public class KangMoDouPeng extends CustomRelic {
    public static final String ID = ModHelper.makePath("KangMoDouPeng");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/KangMoDouPeng.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/KangMoDouPeng.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.COMMON;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法


    public KangMoDouPeng() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new KangMoDouPeng();
    }
    // 遗物效果，可在此处添加代码 TODO
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(p, this));
        this.addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, 2)));
        this.grayscale = true;// 变灰
    }
}
