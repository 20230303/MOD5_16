package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 * "LOLWeaponsMod:GuiSuoDeKuangBaoZhiRen": {
 *     "NAME": "鬼索的狂暴之刃",
 *     "FLAVOR": "刀锋饥渴的嗡鸣，是疯狂吞噬理智的最后通牒。",
 *     "DESCRIPTIONS": ["每回合，每打出3张攻击牌抽1张牌"]
 *   },
 */
public class GuiSuoDeKuangBaoZhiRen extends CustomRelic {
    public static final String ID = ModHelper.makePath("GuiSuoDeKuangBaoZhiRen");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/GuiSuoDeKuangBaoZhiRen.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/GuiSuoDeKuangBaoZhiRen.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = RelicTier.RARE;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法
    public GuiSuoDeKuangBaoZhiRen() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new GuiSuoDeKuangBaoZhiRen();
    }
    // 遗物效果，可在此处添加代码 TODO
    private static final int _Draw = 1;
    private static final int _Count = 3;
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ++this.counter;
            if (this.counter == _Count) {
                flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));//将遗物放置在卡牌上方
                addToBot(new DrawCardAction(AbstractDungeon.player, _Draw));//抽取1张牌
                this.counter = 0;
            }
        } else {
            this.counter = 0;
        }
    }
    @Override
    public void atTurnStart() {
        this.counter = 0;
    }
}
