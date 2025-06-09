package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 *  "LOLWeaponsMod:XianNvHuFu": {
 *     "NAME": "仙女护符",
 *     "FLAVOR": "森林精魄凝结的泪滴，在掌心低语自然秘法。",
 *     "DESCRIPTIONS": ["战斗开始时获得#b%d点能量（可叠加）。"]
 *   },
 */
public class XianNvHuFu extends CustomRelic {
    public static final String ID = ModHelper.makePath("XianNvHuFu");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/XianNvHuFu.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/XianNvHuFu.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = RelicTier.SHOP;//类型:BOSS TODO
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法

    private int count = 1;

    public XianNvHuFu() {
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
        XianNvHuFu copy = new XianNvHuFu();
        copy.count = this.count;
        return copy;
    }

    // 遗物效果，可在此处添加代码 TODO
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(p, this));
        addToTop(new GainEnergyAction(this.count));
    }

    // 当遗物被再次选择时增加计数
    public void incrementCount() {
        this.count++;
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
            XianNvHuFu relic = (XianNvHuFu) AbstractDungeon.player.getRelic(ID);
            relic.incrementCount();
        } else {
            super.obtain();
        }
    }
}
