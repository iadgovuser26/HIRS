package hirs.attestationca.persist.entity.manager;

import hirs.utils.Certificate;
import hirs.attestationca.persist.entity.userdefined.certificate.EndorsementCredential;
import hirs.attestationca.persist.entity.userdefined.certificate.IssuedAttestationCertificate;
import hirs.attestationca.persist.entity.userdefined.certificate.PlatformCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository
public interface CertificateRepository<T extends Certificate> extends JpaRepository<Certificate, UUID> {

    @Query(value = "SELECT * FROM Certificate where id = ?1", nativeQuery = true)
    Certificate getCertificate(UUID uuid);
    @Query(value = "SELECT * FROM Certificate where issuer = ?1 AND DTYPE = ?2", nativeQuery = true)
    List<Certificate> findBySubject(String issuer, String dType);
    @Query(value = "SELECT * FROM Certificate where issuerSorted = ?1 AND  DTYPE = ?2", nativeQuery = true)
    List<Certificate> findBySubjectSorted(String issuedSort, String dType);
    @Query(value = "SELECT * FROM Certificate where DTYPE = ?1", nativeQuery = true)
    List<T> findByAll(String dType);
    @Query(value = "SELECT * FROM Certificate where serialNumber = ?1 AND DTYPE = ?2", nativeQuery = true)
    Certificate findBySerialNumber(BigInteger serialNumber, String dType);
    @Query(value = "SELECT * FROM Certificate where platformSerial = ?1 AND DTYPE = 'PlatformCredential'", nativeQuery = true)
    List<PlatformCredential> byBoardSerialNumber(String boardSerialNumber);
    @Query(value = "SELECT * FROM Certificate where holderSerialNumber = ?1 AND DTYPE = 'PlatformCredential'", nativeQuery = true)
    PlatformCredential getPcByHolderSerialNumber(BigInteger holderSerialNumber);
    @Query(value = "SELECT * FROM Certificate where holderSerialNumber = ?1 AND DTYPE = 'PlatformCredential'", nativeQuery = true)
    List<PlatformCredential> getByHolderSerialNumber(BigInteger holderSerialNumber);
    @Query(value = "SELECT * FROM Certificate where certificateHash = ?1 AND DTYPE = ?2", nativeQuery = true)
    T findByCertificateHash(int certificateHash, String dType);
    EndorsementCredential findByPublicKeyModulusHexValue(String publicKeyModulusHexValue);
    IssuedAttestationCertificate findByDeviceId(UUID deviceId);
    Certificate findByCertificateHash(int certificateHash);
}
