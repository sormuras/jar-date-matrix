import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestOutputStream;
import java.security.MessageDigest;

class Sha256 {

  public static void main(String... args) {
    System.out.println(compute(Path.of(args[0], "SHA-256)));
  }

  public static String compute(Path path, String algorithm) {
    if (Files.notExists(path)) throw new RuntimeException(path.toString());
    try {
      if ("size".equalsIgnoreCase(algorithm)) return Long.toString(Files.size(path));
      var md = MessageDigest.getInstance(algorithm);
      try (var source = new BufferedInputStream(new FileInputStream(path.toFile()));
          var target = new DigestOutputStream(OutputStream.nullOutputStream(), md)) {
        source.transferTo(target);
      }
      return String.format("%0" + (md.getDigestLength() * 2) + "x", new BigInteger(1, md.digest()));
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
