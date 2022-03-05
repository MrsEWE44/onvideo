package com.ov.video.expand.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class VerificationCode {


    private String toBase64(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", stream);
        byte[] encode = Base64.getEncoder().encode(stream.toByteArray());
        return new String(encode);
    }

    private String toHTMLImgSrc(BufferedImage bufferedImage) throws IOException {
        return "data:image/png;base64,"+this.toBase64(bufferedImage);
    }

    private int getXY(int a,int b) {
        OVHttpUtils ovHttpUtils = new OVHttpUtils();
        int random = ovHttpUtils.getRandom(a-10, 0);
        return random>b?random:b;
    }

    private HashMap<String,Object> generate() throws IOException {
        String fname="code_img/fff.jpg";
        Resource resource = new ClassPathResource(fname);
        HashMap<String, Object> hashMap = new HashMap<>();
        String parent = resource.getFile().getParent();
        String oldSrc=parent+"/fff.jpg";
        BufferedImage oldSrcImage = ImageIO.read(new File(oldSrc));
        int h=200,w=400,x=getXY(oldSrcImage.getWidth()-w,w),
                y=getXY(oldSrcImage.getHeight()-h,h);
        BufferedImage outSrcImage = oldSrcImage.getSubimage(x,y,w,h);

        int nh=getXY(outSrcImage.getHeight()/2,10);
        int nw=getXY(outSrcImage.getWidth()/2,10);
        int x1=getXY(outSrcImage.getWidth()-nw,1);
        int y1=getXY(outSrcImage.getHeight()-nh,1);
        BufferedImage partImage = outSrcImage.getSubimage(x1,y1,nw,nh);
        hashMap.put("img2",this.toHTMLImgSrc(partImage));
        BufferedImage outPartImage = new BufferedImage(partImage.getWidth(), partImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outPartImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, partImage.getWidth(), partImage.getHeight());
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
        g2d.setComposite(ac);
        g2d.drawImage(partImage,0,0,partImage.getWidth(),partImage.getHeight(),null);
        g2d.dispose();
        Graphics2D graphics = outSrcImage.createGraphics();
        graphics.drawImage(outPartImage,null,x1,y1);
        graphics.dispose();

        hashMap.put("img1",this.toHTMLImgSrc(outSrcImage));

        hashMap.put("h",outSrcImage.getHeight()- y1);
        hashMap.put("x",x1);
        return hashMap;
    }

    public HashMap<String,Object> getCode(){
        try {
            return generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
