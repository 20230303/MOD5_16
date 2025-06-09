package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 *   "LOLWeaponsMod:QianBianZheJiaXiu": {
 *     "NAME": "千变者贾修",
 *     "FLAVOR": "适应，吞噬，进化——万物终将臣服。",
 *     "DESCRIPTIONS": [
 *       "获得格挡时多获得两点"
 *     ]
 *   },
 */
public class QianBianZheJiaXiu extends CustomRelic {
    public static final String ID = ModHelper.makePath("QianBianZheJiaXiu");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/QianBianZheJiaXiu.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/QianBianZheJiaXiu.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.UNCOMMON;//类型:稀有
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:重击
    public QianBianZheJiaXiu() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new QianBianZheJiaXiu();
    }
    // 遗物效果，可在此处添加代码 TODO
    private static final int EXTRA_BLOCK = 2;
    @Override
    public int onPlayerGainedBlock(float blockAmount) {
        if (blockAmount > 0.0F) {
            blockAmount += EXTRA_BLOCK;
            this.flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        return super.onPlayerGainedBlock(blockAmount);
    }

}
