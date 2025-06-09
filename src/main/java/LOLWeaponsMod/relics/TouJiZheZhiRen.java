package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 *   "LOLWeaponsMod:TouJiZheZhiRen": {
 *     "NAME": "投机者之刃",
 *     "FLAVOR": "赌徒的刀锋永远指向最大收益，哪怕代价是深渊。",
 *     "DESCRIPTIONS": ["每次攻击获得1金币"]
 *   },
 */
public class TouJiZheZhiRen extends CustomRelic {
    public static final String ID = ModHelper.makePath("TouJiZheZhiRen");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/TouJiZheZhiRen.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/TouJiZheZhiRen.png";
    private static final RelicTier RELIC_TIER = RelicTier.RARE;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;//声音:魔法
    public TouJiZheZhiRen() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new TouJiZheZhiRen();
    }
    // 遗物效果，可在此处添加代码 TODO
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            flash();
            AbstractDungeon.player.gainGold(1);
        }
    }
}
