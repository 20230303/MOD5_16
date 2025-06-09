package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 *  "LOLWeaponsMod:ZhiGaoTianNuoYan": {
 *     "NAME": "至高天诺言",
 *     "FLAVOR": "群星见证的誓约，其重可压垮王国。",
 *     "DESCRIPTIONS": ["拾取时，获得999金币"]
 *   },
 */
public class ZhiGaoTianNuoYan extends CustomRelic {
    public static final String ID = ModHelper.makePath("ZhiGaoTianNuoYan");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/ZhiGaoTianNuoYan.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/ZhiGaoTianNuoYan.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.BOSS;//类型：稀有
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//音效：魔法
    public ZhiGaoTianNuoYan() {
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new ZhiGaoTianNuoYan();
    }
    // 遗物效果，可在此处添加代码 TODO
    public void onEquip(){//拾取时触发
        CardCrawlGame.sound.play("GOLD_GAIN");//播放音效
        AbstractDungeon.player.gainGold(999);//获得33金币
    }
}
