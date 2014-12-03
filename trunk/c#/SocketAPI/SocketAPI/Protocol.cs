using System;
using System.Collections.Generic;
using System.Text;
using TigerNetwork;

namespace BowlingCommon
{
	/// <remarks>
	/// 클라이언트가 서버로 질의를 하고 이에 응답을 해주는 경우, 프로토콜 이름은 QryXXX, RplXXX 로 명명합니다(Query/Reply).
	/// 클라이언트가 서버에게 응답이 필요없는 명령을 내릴경우, 프로토콜 이름은 CmdXXX 로 명명합니다(Command).
	/// 
	/// 서버가 클라이언트에게 응답이 필요없는 정보를 줄 경우, 프로토콜 이름은 InfXXX 로 명명합니다(Inform).
	/// </remarks>
	public enum Protocol
	{
		Ping = 1,


		MainScenePacketStart = 50,

		QryUserMakeRoom = 51,
		RplUserMakeRoom = 52,

		QryCostumeEquip = 53,
		RplCostumeEquip = 54,

		QryChangeCurrentPet = 55,
		RplChangeCurrentPet = 56,

		QryGetMailInfo = 57,
		RplGetMailInfo = 58,

		CmdDeleteMail = 59,

		QryCheckWeeklyRecord = 60,
		RplCheckWeeklyRecord = 61,

		QryGetNoticeInfo = 62,
		RplGetNoticeInfo = 63,

		QryReceiveAllItemMail = 64,
		RplReceiveAllItemMail = 65,

		QryReceiveMailItem = 66,
		RplReceiveMailItem = 67,

		QrySendEnergy = 68,
		RplSendEnergy = 69,

		QrySocialPointGacha = 70,
		RplSocialPointGacha = 71,

		QryTodayMissionGacha = 72,
		RplTodayMissionGacha = 73,

		QryPetLevelup = 74,
		RplPetLevelup = 75,

		QryCardEquip = 76,
		RplCardEquip = 77,

		QryCardMerge = 78,
		RplCardMerge = 79,

		QryCardStrengthen = 80,
		RplCardStrengthen = 81,

		QryExtendCardInventory = 82,
		RplExtendCardInventory = 83,

		QryOpenCardEquipSlot = 84,
		RplOpenCardEquipSlot = 85,

		QryCardSale = 86,
		RplCardSale = 87,

		MainScenePacketEnd = 99,
		/// <summary>
		/// waiting room packet
		/// </summary>
		WaitRoomPacketStart = 100,

		CmdQuickMatch = 101,

		//QryUserFriendList = 105,
		//RplUserFriendList = 106,

		//QryUserInvite = 107,
		//RplUserInvite = 108,

		QryUserJoinRoom = 109,
		RplUserJoinRoom = 110,

		CmdUserReady = 111,

		InfUserJoinRoom = 112,
		InfUserReady = 113,

		InfAllUserReady = 114,

		InfGameContinue = 118,

		QrySessionUserList = 119,
		RplSessionUserList = 120,

		CmdItemListChanged = 121,
		InfItemListChanged = 122,

		CmdRemoveUser = 123,

		QryRandomItem = 124,
		RplRandomItem = 125,

		CmdSceneLoadComplete = 126,

		CmdChangeRoom = 127,
		InfRoomChanged = 128,

		CmdChangeGameSpeed = 129,


		QryBlockSpeedUnlock = 130,
		RplBlockSpeedUnlock = 131,

		InfBlockSpeedUnlock = 132,

		WaitRoomPacketEnd = 199,

		/// <summary>
		/// game room packet
		/// </summary>
		GameRoomPacketStart = 200,

		CmdGameSceneReady = 201,

		InfStartCountDown = 202,

		CmdUserTryTyping = 204,

		QryUserTryAnswer = 205,
		RplUserTryAnswer = 206,

		InfGameStart = 211,
		InfGameEnd = 212,
		InfUserTyping = 214,

		InfWordMatrixChanged = 215,
		InfUserGameOver = 216,
		
		InfBlockAdd = 223,
		InfItemBlockGenerated = 224,
		InfBlockDestroyed = 225,
		InfNextBlockUpdate = 226,
		InfBlockChanged = 227,
		InfBlockGearUpdate = 228,
		InfGetCoin = 229,
		InfBlockItemDisable = 230,
		InfShieldBreak = 231,
		InfShieldStart = 232,

		CmdRematch = 233,
		InfRematch = 234,

		InfAllRematch = 235,

		CmdNewGameSession = 236,

		InfBroadcastAddItemBlock = 238,
		InfBroadcastDeleteItemBlock = 239,
		InfBroadcastAddRow = 240,
		InfBroadcastRemoveRow = 241,

		CmdChangeTargetUser = 242,
		CmdChangeDefenseTargetUser = 243,

		InfClearMatrix = 244,
		InfGetCash = 245,
		InfGetChestBox = 246,

		InfBlockItemEffect = 247,

		GameRoomPacketEnd = 299,

		/// <summary>
		/// common packet
		/// </summary>
		GameSessionCommonPacketStart = 500,

		CmdChatMessage = 501,
		InfChatMessage = 502,
		InfUserLeave = 503,

		QryLogin = 504,
		RplLogin = 505,

		CmdInviteFriend = 506, // 일단은 common 패킷에..
		InfInvited = 507, // 일단은 common 패킷에..

		CmdUserLeave = 508,

		InfOwnerChanged = 509,

		CmdCheckEnergy = 510,
		InfEnergyUpdate = 511,

		QryCostumeInfo = 512,
		RplCostumeInfo = 513,

		GameSessionCommonPacketEnd = 599,

		InfHello = 604,
		InfException = 605,

		QryMonitor = 606, // 게임 서버로 보내는 페킷
		RplMonitor = 607, // 게임 서버에서 반환하는 페킷

		QryServerStatus = 608, // 운영툴에서 보내는 페킷
		RplServerStatus = 609, // 운영툴로 반환하는 페킷

		CmdShutdownServer = 610,
		CmdStartServer = 611,

		CmdGatewayInviteFriends = 612,
		InfGatewayInviteFriends = 613,
		
		QryGatewayGameServerConnect = 614,
		RplGatewayGameServerConnect = 615,

		InfGatewayGameUserLogin = 616,
		InfGatewayGameUserLogout = 617,

		QryAcceptInvitation = 618,
		RplAcceptInvitation = 619,

		QryGatewayAcceptInvitationSG = 620,
		QryGatewayAcceptInvitationGS = 621,

		RplGatewayAcceptInvitationSG = 622,
		RplGatewayAcceptInvitationGS = 623,
	}

	/// <summary>
	/// 
	/// </summary>
	public enum ErrorCode
	{
		Succeed = 0,

		SessionFull,
		SessionStatusWrong,
		SessionSlotError,
		NoAvailableGameSession,
		IllegalMatchType,
		MakeRoomAlreadyInRoom,
		AcceptInvitationSessionFull,
		AcceptInvitationSessionIsNotWaiting,
		IllegalUserID,
		IllegalCostumeSlotLength,
		DuplicateCostumeSRLInSlots,
		IllegalCostumeSRL,
		IllegalCostumeEquipSlot,
		DuplicateCurrentPetID,
		IllegalPetID,
		IllegalMailId,
		NotEnoughSocialPointForGacha,
		CashNotEnough,
		MissionNotCompleted,
		PetReachMaxLevel,
		GameMoneyNotEnough,
		CardEquipIllegalSlotDat,
		MergeCardDuplicateCardId,
		MergeCardIllegalCardId,
		CardStrengthenIllegalCardId,
		CardStrengthenReachMaxLevel,
		MergeCardCannotMergeEquipedCard,
		ExtendCardInventoryReachMaxCapacity,
		OpenCardEquipSlotAllSlotOpened,
		CardSaleIllegalCardId,
		CardSaleCannotSaleEquipedCard,
		BlockSpeedReachMaxValue,
	}

	public enum GameResultCode
	{
		BubbleRight,
		BubbleAlreadyBroken,
		BubbleWrongAnswer,
	}

	public struct InfExceptionPacket : IPacketStruct
	{
		public string Message;

		public InfExceptionPacket( string message )
		{
			Message = message;
		}

		#region IPacketStruct Members

		public void WriteTo( Packet packet )
		{
			packet.Write( Message );
		}

		public void ReadFrom( Packet packet )
		{
			packet.Read( out Message );
		}

		#endregion
	}
}

