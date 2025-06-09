package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 *   "LOLWeaponsMod:ZhiLiaoBaoZhu": {
 *     "NAME": "治疗宝珠",
 *     "FLAVOR": "伊甸园露珠凝成的翡翠，流转着生命原初的脉动。",
 *     "DESCRIPTIONS": ["战斗开始时获得2点再生（可叠加）"]
 *   },
 */
public class ZhiLiaoBaoZhu extends CustomRelic {
    public static final String ID = ModHelper.makePath("ZhiLiaoBaoZhu");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/ZhiLiaoBaoZhu.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/ZhiLiaoBaoZhu.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = RelicTier.SHOP;//类型:BOSS TODO
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法

    private int count = 2;

    public ZhiLiaoBaoZhu() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
        // 初始化描述
        this.description = String.format(this.DESCRIPTIONS[0], count);
        this.updateDescription();
    }

    private void updateDescription() {
        this.description = String.format(this.DESCRIPTIONS[0], count);
    }

    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0], count);
    }

    public AbstractRelic makeCopy() {
        ZhiLiaoBaoZhu copy = new ZhiLiaoBaoZhu();
        copy.count = this.count;
        return copy;
    }

    // 遗物效果，可在此处添加代码 TODO
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new RegenPower(p, this.count)));
    }

    // 当遗物被再次选择时增加计数
    @Override
    public void onEquip() {
        this.count+=2;//++为加1,若为加2，则修改为count += 2;

        this.description = String.format(this.DESCRIPTIONS[0], count);
        this.updateDescription();
    }


    // 使遗物可以再次出现在遗物选择界面
    @Override
    public boolean canSpawn() {
        return true;
    }

    // 覆盖 obtain 方法以增加计数而不是添加新的遗物
    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(ID)) {
            ZhiLiaoBaoZhu relic = (ZhiLiaoBaoZhu) AbstractDungeon.player.getRelic(ID);
            relic.onEquip();
        } else {
            super.obtain();
        }
    }
}
