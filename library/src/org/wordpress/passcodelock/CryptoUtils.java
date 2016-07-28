package org.wordpress.passcodelock;

import android.content.Context;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CryptoUtils {
  private static Crypto crypto = null;
  private static Entity entity = null;

  private CryptoUtils() {
  }

  /**
   * 初始化
   *
   * @param context context
   * @param e 密码
   * @return 是否成功
   */
  public static boolean init(Context context, String e) {
    entity = Entity.create(e);
    crypto = new Crypto(new SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256),
        new SystemNativeCryptoLibrary(), CryptoConfig.KEY_256);

    // Check for whether the crypto functionality is available
    // This might fail if android does not load libaries correctly.
    return crypto.isAvailable();
  }

  public static void destory() {
    crypto = null;
    entity = null;
  }

  public static OutputStream getCipherOutputStream(String filePath) {
    File file = new File(filePath);
    return getCipherOutputStream(file);
  }

  public static OutputStream getCipherOutputStream(File file) {
    if (!file.exists()) return null;
    try {
      OutputStream fileStream = new FileOutputStream(file);
      return crypto.getCipherOutputStream(fileStream, entity);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (CryptoInitializationException e) {
      e.printStackTrace();
    } catch (KeyChainException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static InputStream getCipherInputStream(String file) {
    return getCipherInputStream(new File(file));
  }

  public static InputStream getCipherInputStream(File file) {
    if (!file.exists()) return null;
    try {
      InputStream inputStream = new FileInputStream(file);
      return crypto.getCipherInputStream(inputStream, entity);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (KeyChainException e) {
      e.printStackTrace();
    } catch (CryptoInitializationException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
