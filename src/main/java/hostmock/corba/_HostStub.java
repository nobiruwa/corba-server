package hostmock.corba;


/**
* hostmock/corba/_HostStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idl/Host.idl
* Sunday, December 10, 2017 12:22:07 PM UTC
*/

public class _HostStub extends org.omg.CORBA.portable.ObjectImpl implements hostmock.corba.Host
{

  public void sndAndRcv (String inq, org.omg.CORBA.StringHolder ans)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("sndAndRcv", true);
                $out.write_wstring (inq);
                $in = _invoke ($out);
                ans.value = $in.read_wstring ();
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                sndAndRcv (inq, ans        );
            } finally {
                _releaseReply ($in);
            }
  } // sndAndRcv

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:hostmock/corba/Host:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _HostStub
