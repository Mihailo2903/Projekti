USE [master]
GO
/****** Object:  Database [ProjekatSab]    Script Date: 03-Jun-23 14:54:18 ******/
CREATE DATABASE [ProjekatSab]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ProjekatSab', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\ProjekatSab.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'ProjekatSab_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\ProjekatSab_log.ldf' , SIZE = 73728KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [ProjekatSab] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ProjekatSab].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ProjekatSab] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ProjekatSab] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ProjekatSab] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ProjekatSab] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ProjekatSab] SET ARITHABORT OFF 
GO
ALTER DATABASE [ProjekatSab] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ProjekatSab] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ProjekatSab] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ProjekatSab] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ProjekatSab] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ProjekatSab] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ProjekatSab] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ProjekatSab] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ProjekatSab] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ProjekatSab] SET  DISABLE_BROKER 
GO
ALTER DATABASE [ProjekatSab] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ProjekatSab] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ProjekatSab] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ProjekatSab] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ProjekatSab] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ProjekatSab] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ProjekatSab] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ProjekatSab] SET RECOVERY FULL 
GO
ALTER DATABASE [ProjekatSab] SET  MULTI_USER 
GO
ALTER DATABASE [ProjekatSab] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ProjekatSab] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ProjekatSab] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ProjekatSab] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [ProjekatSab] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [ProjekatSab] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'ProjekatSab', N'ON'
GO
ALTER DATABASE [ProjekatSab] SET QUERY_STORE = OFF
GO
USE [ProjekatSab]
GO
/****** Object:  Table [dbo].[Article]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Article](
	[IdArticle] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[Price] [decimal](10, 3) NOT NULL,
	[IdShop] [int] NOT NULL,
	[Count] [int] NOT NULL,
 CONSTRAINT [XPKArticle] PRIMARY KEY CLUSTERED 
(
	[IdArticle] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Buyer]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Buyer](
	[IdBuyer] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[Credit] [decimal](10, 3) NOT NULL,
	[IdCity] [int] NOT NULL,
 CONSTRAINT [XPKBuyer] PRIMARY KEY CLUSTERED 
(
	[IdBuyer] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[City]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[City](
	[IdCity] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](100) NOT NULL,
 CONSTRAINT [XPKCity] PRIMARY KEY CLUSTERED 
(
	[IdCity] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Item]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Item](
	[IdItem] [int] IDENTITY(1,1) NOT NULL,
	[IdArticle] [int] NOT NULL,
	[IdOrder] [int] NOT NULL,
	[Count] [int] NOT NULL,
	[Cost] [decimal](10, 3) NULL,
 CONSTRAINT [XPKItem] PRIMARY KEY CLUSTERED 
(
	[IdItem] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Line]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Line](
	[IdCity1] [int] NOT NULL,
	[IdCity2] [int] NOT NULL,
	[Distance] [int] NOT NULL,
	[IdLine] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [XPKLine] PRIMARY KEY CLUSTERED 
(
	[IdLine] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Location]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Location](
	[IdLocation] [int] IDENTITY(1,1) NOT NULL,
	[IdCity] [int] NOT NULL,
	[IdOrder] [int] NOT NULL,
	[Date] [datetime] NOT NULL,
 CONSTRAINT [XPKLocation] PRIMARY KEY CLUSTERED 
(
	[IdLocation] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order1]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order1](
	[IdOrder] [int] IDENTITY(1,1) NOT NULL,
	[IdBuyer] [int] NOT NULL,
	[FinalPrice] [decimal](10, 3) NULL,
	[Status] [varchar](50) NULL,
	[FullPrice] [decimal](10, 3) NULL,
	[DateOfSending] [datetime] NULL,
	[DateOfAssembly] [datetime] NULL,
	[DateOfArriving] [datetime] NULL,
	[IdCityClosest] [int] NULL,
 CONSTRAINT [XPKOrder] PRIMARY KEY CLUSTERED 
(
	[IdOrder] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Shop]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Shop](
	[IdShop] [int] IDENTITY(1,1) NOT NULL,
	[IdCity] [int] NOT NULL,
	[Name] [varchar](100) NOT NULL,
	[Discount] [int] NOT NULL,
 CONSTRAINT [XPKShop] PRIMARY KEY CLUSTERED 
(
	[IdShop] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Transaction1]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Transaction1](
	[IdTransaction] [int] IDENTITY(1,1) NOT NULL,
	[IdBuyer] [int] NULL,
	[Amount] [decimal](10, 3) NOT NULL,
	[Date] [datetime] NOT NULL,
	[IdShop] [int] NULL,
	[IdOrder] [int] NULL,
	[Type] [char](1) NOT NULL,
 CONSTRAINT [XPKTransaction] PRIMARY KEY CLUSTERED 
(
	[IdTransaction] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Buyer] ADD  CONSTRAINT [Nula]  DEFAULT ((0)) FOR [Credit]
GO
ALTER TABLE [dbo].[Shop] ADD  CONSTRAINT [Default_Value_197_641091661]  DEFAULT ((0)) FOR [Discount]
GO
ALTER TABLE [dbo].[Article]  WITH CHECK ADD  CONSTRAINT [R_1] FOREIGN KEY([IdShop])
REFERENCES [dbo].[Shop] ([IdShop])
GO
ALTER TABLE [dbo].[Article] CHECK CONSTRAINT [R_1]
GO
ALTER TABLE [dbo].[Buyer]  WITH CHECK ADD  CONSTRAINT [R_2] FOREIGN KEY([IdCity])
REFERENCES [dbo].[City] ([IdCity])
GO
ALTER TABLE [dbo].[Buyer] CHECK CONSTRAINT [R_2]
GO
ALTER TABLE [dbo].[Item]  WITH CHECK ADD  CONSTRAINT [R_7] FOREIGN KEY([IdArticle])
REFERENCES [dbo].[Article] ([IdArticle])
GO
ALTER TABLE [dbo].[Item] CHECK CONSTRAINT [R_7]
GO
ALTER TABLE [dbo].[Item]  WITH CHECK ADD  CONSTRAINT [R_8] FOREIGN KEY([IdOrder])
REFERENCES [dbo].[Order1] ([IdOrder])
GO
ALTER TABLE [dbo].[Item] CHECK CONSTRAINT [R_8]
GO
ALTER TABLE [dbo].[Line]  WITH CHECK ADD  CONSTRAINT [R_4] FOREIGN KEY([IdCity1])
REFERENCES [dbo].[City] ([IdCity])
GO
ALTER TABLE [dbo].[Line] CHECK CONSTRAINT [R_4]
GO
ALTER TABLE [dbo].[Line]  WITH CHECK ADD  CONSTRAINT [R_5] FOREIGN KEY([IdCity2])
REFERENCES [dbo].[City] ([IdCity])
GO
ALTER TABLE [dbo].[Line] CHECK CONSTRAINT [R_5]
GO
ALTER TABLE [dbo].[Location]  WITH CHECK ADD  CONSTRAINT [R_15] FOREIGN KEY([IdCity])
REFERENCES [dbo].[City] ([IdCity])
GO
ALTER TABLE [dbo].[Location] CHECK CONSTRAINT [R_15]
GO
ALTER TABLE [dbo].[Location]  WITH CHECK ADD  CONSTRAINT [R_16] FOREIGN KEY([IdOrder])
REFERENCES [dbo].[Order1] ([IdOrder])
GO
ALTER TABLE [dbo].[Location] CHECK CONSTRAINT [R_16]
GO
ALTER TABLE [dbo].[Order1]  WITH CHECK ADD  CONSTRAINT [R_3] FOREIGN KEY([IdBuyer])
REFERENCES [dbo].[Buyer] ([IdBuyer])
GO
ALTER TABLE [dbo].[Order1] CHECK CONSTRAINT [R_3]
GO
ALTER TABLE [dbo].[Order1]  WITH CHECK ADD  CONSTRAINT [R_9] FOREIGN KEY([IdCityClosest])
REFERENCES [dbo].[City] ([IdCity])
GO
ALTER TABLE [dbo].[Order1] CHECK CONSTRAINT [R_9]
GO
ALTER TABLE [dbo].[Shop]  WITH CHECK ADD  CONSTRAINT [R_6] FOREIGN KEY([IdCity])
REFERENCES [dbo].[City] ([IdCity])
GO
ALTER TABLE [dbo].[Shop] CHECK CONSTRAINT [R_6]
GO
ALTER TABLE [dbo].[Transaction1]  WITH CHECK ADD  CONSTRAINT [R_10] FOREIGN KEY([IdBuyer])
REFERENCES [dbo].[Buyer] ([IdBuyer])
GO
ALTER TABLE [dbo].[Transaction1] CHECK CONSTRAINT [R_10]
GO
ALTER TABLE [dbo].[Transaction1]  WITH CHECK ADD  CONSTRAINT [R_13] FOREIGN KEY([IdShop])
REFERENCES [dbo].[Shop] ([IdShop])
GO
ALTER TABLE [dbo].[Transaction1] CHECK CONSTRAINT [R_13]
GO
ALTER TABLE [dbo].[Transaction1]  WITH CHECK ADD  CONSTRAINT [R_14] FOREIGN KEY([IdOrder])
REFERENCES [dbo].[Order1] ([IdOrder])
GO
ALTER TABLE [dbo].[Transaction1] CHECK CONSTRAINT [R_14]
GO
/****** Object:  StoredProcedure [dbo].[SP_FINAL_PRICE]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_FINAL_PRICE]
	@OrdId int,
	@CurrDate date,
	@Allowed int output,
	@CityId int output
AS
BEGIN
	declare @FinalPrice decimal(10,3);
	declare @FullPrice decimal(10,3);
    declare @amount decimal(10,3);
	declare @BuyerId int;
	
	select @amount = b.Credit, @BuyerId = b.IdBuyer  
	from Order1 o, Buyer b
	where o.IdOrder = @OrdId and b.IdBuyer = o.IdBuyer;

	select @FullPrice = coalesce(sum(i.Count * a.Price),0), @FinalPrice = coalesce(sum(i.Count * a.Price * (100-s.Discount) / 100),0) 
	from
	Item i, Article a, Shop s
	where i.IdOrder = @OrdId and i.IdArticle = a.IdArticle and a.IdShop = s.IdShop;

	if(@amount >= @FinalPrice)
	begin
		set @Allowed = 1;
		update Buyer
		set Credit = Credit - @FinalPrice where IdBuyer = @BuyerId;
		update Order1
		set Status = 'sent', FinalPrice = @FinalPrice, FullPrice = @FullPrice where IdOrder = @OrdId;

		insert into Transaction1(IdBuyer,Amount,Date,IdOrder,Type) values (@BuyerId, @FinalPrice, @CurrDate, @OrdId, 'B');

		select @CityId = IdCity 
		from Buyer where IdBuyer = @BuyerId 

		--calculate price for every item in order
		declare @cursor cursor;
		declare @ItemId int;
		declare @price decimal(10,3);

		set @cursor = cursor for 
		select IdItem from Item where IdOrder = @OrdId

		open @cursor
		fetch next from @cursor into @ItemId

		while @@FETCH_STATUS = 0
		begin
			select @price = i.Count * a.Price * (100-s.Discount) / 100
			from Item i, Article a, Shop s
			where i.IdItem = @ItemId and i.IdArticle = a.IdArticle and a.IdShop = s.IdShop;

			update Item
			set Cost = @price where IdItem = @ItemId;

			fetch next from @cursor into @ItemId;
		end

		close @cursor
		deallocate @cursor
	end
	else
		set @Allowed = 0;

END
GO
/****** Object:  Trigger [dbo].[TR_TRANSFER_MONEY_TO_SHOPS]    Script Date: 03-Jun-23 14:54:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [dbo].[TR_TRANSFER_MONEY_TO_SHOPS]
   ON  [dbo].[Order1]
   AFTER Update
AS 
BEGIN
	declare @cursor cursor;
	declare @OrderId int, @BuyerId int;
	declare @DateOfArriving date;
	declare @discount int;

	set @cursor = cursor for
	select IdOrder, IdBuyer, DateOfArriving
	from inserted where Status = 'arrived';

	open @cursor;

	fetch next from @cursor
	into @OrderId, @BuyerId, @DateOfArriving;

	while @@FETCH_STATUS = 0
	begin
		if exists(
			select *
			from Transaction1
			where IdBuyer = @BuyerId and Type = 'B' and Amount > 10000 and abs(DATEDIFF(DAY,@DateOfArriving,Date)) <= 30
		)
			set @discount = 3;
		else
			set @discount = 5;

		declare @cursorItem cursor;
		declare @ShopId int, @cost decimal(10,3);

		set @cursorItem = cursor for
		select a.IdShop, sum(i.Cost) as ItemSum
		from Item i, Article a
		where i.IdOrder = @OrderId and i.IdArticle = a.IdArticle
		group by a.IdShop;

		open @cursorItem;
		fetch next from @cursorItem
		into @ShopId, @cost;

		while @@FETCH_STATUS = 0
		begin
			insert into Transaction1(IdBuyer,Amount,Date,IdOrder,Type,IdShop) values (@BuyerId, @cost * (100-@discount) / 100, @DateOfArriving, @OrderId, 'S', @ShopId);
			insert into Transaction1(IdBuyer,Amount,Date,IdOrder,Type,IdShop) values (@BuyerId, @cost * @discount / 100, @DateOfArriving, @OrderId, 'Y', @ShopId);
		
			fetch next from @cursorItem
			into @ShopId, @cost;
		end

		close @cursorItem;
		deallocate @cursorItem;

		fetch next from @cursor
		into @OrderId, @BuyerId, @DateOfArriving;
	end
	close @cursor;
	deallocate @cursor;

END
GO
ALTER TABLE [dbo].[Order1] ENABLE TRIGGER [TR_TRANSFER_MONEY_TO_SHOPS]
GO
USE [master]
GO
ALTER DATABASE [ProjekatSab] SET  READ_WRITE 
GO
