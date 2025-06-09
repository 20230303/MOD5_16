package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STARTER_DEFEND;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STARTER_STRIKE;

/**
 *  "LOLWeaponsMod:LanShuiJing": {
 *     "NAME": "蓝水晶",
 *     "FLAVOR": "海洋的魂魄在晶体中搏动，等待觉醒的刹那。",
 *     "DESCRIPTIONS": ["每回合第一次使用打击或防御后获得1点能量"]
 *   },
 */
public class LanShuiJing extends CustomRelic {
    public static final String ID = ModHelper.makePath("LanShuiJing");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/LanShuiJing.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/LanShuiJing.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = RelicTier.COMMON;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法
    public LanShuiJing() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new LanShuiJing();
    }
    // 遗物效果，可在此处添加代码 TODO
    private boolean usedThisTurn = false;//是否已使用过

    @Override
    public void atTurnStart() {
        usedThisTurn = false; // 每回合开始时重置计数器
        this.grayscale = false; // 取消灰色
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action){
        if (!usedThisTurn && (card.hasTag(STARTER_STRIKE) || card.hasTag(STARTER_DEFEND))) {//使用打击或防御卡时触发，前提是未使用过
            flash();
            addToTop(new GainEnergyAction(1));//获得1点能量
            usedThisTurn = true;
            this.grayscale = true; // 变灰
        }
    }

}
